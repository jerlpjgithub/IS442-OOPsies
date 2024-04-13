package com.oopsies.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

/**
 * The Image class is used to instantiate and store images within the database.
 */
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    /**
     * Default constructor required by Hibernate.
     */
    public Image() {}

    // ----------------- Getter and Setter methods (Start) ----------------
/**
 * Returns the ID of the Image.
 *
 * @return A Long representing the ID of the Image.
 */
public Long getId() {
    return this.id;
}

/**
 * Sets the ID of the Image.
 *
 * @param id The ID to set.
 */
public void setId(Long id) {
    this.id = id;
}

/**
 * Returns the name of the Image.
 *
 * @return A string representing the name of the Image.
 */
public String getName() {
    return this.name;
}

/**
 * Sets the name of the Image.
 *
 * @param name The name to set.
 */
public void setName(String name) {
    this.name = name;
}

/**
 * Returns the type of the Image.
 *
 * @return A string representing the type of the Image.
 */
public String getType() {
    return this.type;
}

/**
 * Sets the type of the Image.
 *
 * @param type The type to set.
 */
public void setType(String type) {
    this.type = type;
}

/**
 * Returns the image data of the Image.
 *
 * @return A byte array representing the image data of the Image.
 */
public byte[] getImageData() {
    return this.imageData;
}

/**
 * Sets the image data of the Image.
 *
 * @param imageData The image data to set.
 */
public void setImageData(byte[] imageData) {
    this.imageData = imageData;
}
    // ----------------- Getter and Setter methods (End) ----------------

}
