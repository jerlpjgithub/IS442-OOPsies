import React, {
  useState,
  createContext,
  useEffect,
  useContext,
  useCallback,
} from "react";
import axios from "axios";

// Set withCredentials globally if your backend relies on it for session or cookie handling
axios.defaults.withCredentials = true;

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [authUser, setAuthUser] = useState(() => {
    const user = localStorage.getItem("authUser");
    return user ? JSON.parse(user) : null;
  });
  const [isAuthenticated, setAuthenticated] = useState(
    localStorage.getItem("isAuthenticated") === "true"
  );

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/auth/status",
        {
          withCredentials: true,
        }
      );
      if (response.status === 200 && response.data) {
        setAuthenticated(true);
        localStorage.setItem("isAuthenticated", "true");
        setAuthUser(response.data); // Assume response.data is the user object
        localStorage.setItem("authUser", JSON.stringify(response.data));
      } else {
        setAuthUser(null);
        localStorage.removeItem("authUser");
        setAuthenticated(false);
        localStorage.removeItem("isAuthenticated");
      }
    } catch (error) {
      console.error("Checking auth status failed:", error);
      refreshAccessToken().catch(() => {});
    }
  };

  const googleLogin = async (credentialResponse) => {
    try {
      const response = await axios.post(process.env.REACT_APP_GOOGLE_REDIRECT_URI, credentialResponse);
      if (response.data && response.status === 200) {
        setAuthUser(response.data);
        localStorage.setItem("authUser", JSON.stringify(response.data));
        setAuthenticated(true);
        localStorage.setItem("isAuthenticated", "true");
      }
      return response;
    } catch (error) {
      console.log("login failed", error);
      throw error;
    }
  };

  const login = async (email, password) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        { email, password }
      );
      if (response.data && response.status === 200) {
        setAuthUser(response.data); // responseData contains id, email, roles[]
        localStorage.setItem("authUser", JSON.stringify(response.data));
        setAuthenticated(true);
        localStorage.setItem("isAuthenticated", "true");
      }
      return response;
    } catch (error) {
      console.error("Login failed:", error);
      throw error;
    }
  };

  const register = async (email, password, firstName, lastName) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        { email, password, firstName, lastName }
      );
      return response;
    } catch (error) {
      console.error("Registration failed:", error);
      throw error;
    }
  };

  const logout = useCallback(async () => {
    try {
      await axios.post("http://localhost:8080/api/auth/logout");
      setAuthUser(null);
      localStorage.removeItem("authUser");
      setAuthenticated(false);
      localStorage.removeItem("isAuthenticated");
    } catch (error) {
      console.error("Logout failed:", error);
    }
  }, []);

  const refreshAccessToken = async () => {
    try {
      await axios.post("http://localhost:8080/api/auth/refreshtoken");
    } catch {
      setAuthenticated(false);
      localStorage.removeItem("isAuthenticated");
      setAuthUser(null);
      localStorage.removeItem("authUser");
      // Silently handle the refresh token failure
    }
  };

  axios.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;
      if (
        error.response &&
        error.response.headers["token-expired"] === "true"
      ) {
        originalRequest._retry = true;
        try {
          await refreshAccessToken();
          return axios(originalRequest);
        } catch {
          // Silently catch errors during the refresh token process
        }
      }
      return Promise.reject(error);
    }
  );

  return (
    <AuthContext.Provider
      value={{ authUser, isAuthenticated, login, logout, register, googleLogin }}
    >
      {children}
    </AuthContext.Provider>
  );
};
