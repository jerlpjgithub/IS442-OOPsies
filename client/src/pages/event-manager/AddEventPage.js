import React, { useState, useEffect } from 'react';
import { Form, Input, Button, DatePicker, InputNumber, message, Typography, Tabs, Table, Modal } from 'antd';
import { createEvent, getAllEvents, updateEvent, cancelEvent } from '../../utils/api.js';
import moment from 'moment';

const { Title } = Typography;
const { TabPane } = Tabs;

export const AddEventPage = () => {
  const [form] = Form.useForm();
  const [events, setEvents] = useState([]);
  const [editingEvent, setEditingEvent] = useState(null);

  const authUser = JSON.parse(localStorage.getItem("authUser"));

  useEffect(() => {
    const loadEvents = async () => {
      const fetchedEvents = await getAllEvents();
      setEvents(fetchedEvents.map(event => ({ ...event, dateTime: moment(event.dateTime) }))); // Convert dateTime to moment
    };

    loadEvents();
  }, []);

  const onFinish = async (values) => {
    const requestBody = {
      event: {
        ...values,
        dateTime: values.dateTime.toISOString(),
      },
      managerId: authUser.id,
    };

    try {
      const response = await createEvent(requestBody);
      console.log(response);
      if (response && response.status == 201) {
        message.success(response.message);
        setEvents(prev => [...prev, { ...response.data, dateTime: moment(response.data.dateTime) }]); // Ensure new event's dateTime is also a moment object
        form.resetFields();
      } else {
        message.error('Failed to create event.');
      }
    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 400) {
        message.error(error.response.data.message);
      } else {
        message.error('An error occurred while creating the event.');
      }
    }
  };

  const handleDelete = (record) => {
    Modal.confirm({
      title: 'Are you sure you want to cancel this event?',
      content: 'This will cancel the event and initiate a refund process.',
      okText: 'Yes, cancel it',
      cancelText: 'No, keep it',
      onOk: async () => {
        try {
          // Assuming `cancelEvent` is your API call to cancel the event and initiate refund
          const response = await cancelEvent(record.id);
          if (response && response.status === 200) {
            message.success('Event canceled successfully!');
            // Optionally remove the event from the state to update the UI
            setEvents(events.filter(event => event.id !== record.id));
          } else {
            message.error('Failed to cancel the event.');
          }
        } catch (error) {
          console.error('Error cancelling the event:', error);
          message.error('An error occurred while cancelling the event.');
        }
      },
    });
  };

  const handleEdit = (record) => {
    setEditingEvent(record);
    form.setFieldsValue({
      ...record,
      dateTime: moment(record.dateTime), // Ensure dateTime is set as a moment object for DatePicker
    });
  };

  const handleUpdate = async (eventId, values) => {
    const requestBody = {
      event: {
        ...values,
        dateTime: values.dateTime.toISOString(),
      },
      managerId: authUser.id,
    };

    try {
      const response = await updateEvent(eventId, requestBody);
      if (response && response.status === 201) {
        message.success('Event updated successfully!');
        setEvents(events.map(event => event.id === eventId ? { ...event, ...values, dateTime: moment(values.dateTime) } : event));
        setEditingEvent(null);
      } else {
        message.error('Update failed.');
      }
    } catch (error) {
      console.error(error);
      message.error('An error occurred while updating the event.');
    }
  };

  const validateMessages = {
    required: '${label} is required!',
    types: {
      number: '${label} is not a valid number!',
    },
  };

  const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Event Name', dataIndex: 'eventName', key: 'eventName' },
    {
      title: 'Date and Time',
      dataIndex: 'dateTime',
      key: 'dateTime',
      render: (text) => moment(text).format('Do MMMM YYYY, h:mm a')
    },
    { title: 'Venue', dataIndex: 'venue', key: 'venue' },
    { title: 'Capacity', dataIndex: 'capacity', key: 'capacity' },
    { title: 'Ticket Price', dataIndex: 'ticketPrice', key: 'ticketPrice' },
    { title: 'Cancellation Fee', dataIndex: 'cancellationFee', key: 'cancellationFee' },
    {
      title: 'Action',
      key: 'action',
      render: (_, record) => (
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button onClick={() => handleEdit(record)}>Edit</Button>
          <Button style={{ marginLeft: '10px' }} onClick={() => handleDelete(record)}>Cancel</Button>
        </div>
      ),
    }
  ];

  return (
    <>
      <Tabs defaultActiveKey="1" style={{ marginLeft: '20px' }}>
        <TabPane tab="Create Event" key="1">
          <Title level={2} style={{ marginLeft: '30px' }}>Create Event</Title>
          <Form
            form={form}
            name="event_form"
            onFinish={onFinish}
            validateMessages={validateMessages}
            layout="vertical"
            initialValues={{
              cancellationFee: 0.0,
              ticketPrice: 0.0,
            }}
            style={{ margin: '30px ' }}
          >
            <Form.Item
              name="eventName"
              label="Event Name"
              rules={[{ required: true }]}
            >
              <Input placeholder="Enter the event name" />
            </Form.Item>

            <Form.Item
              name="dateTime"
              label="Event Date and Time"
              rules={[{ required: true }]}
            >
              <DatePicker showTime />
            </Form.Item>

            <Form.Item
              name="venue"
              label="Venue"
              rules={[{ required: true }]}
            >
              <Input placeholder="Enter the event venue" />
            </Form.Item>

            <Form.Item
              name="capacity"
              label="Capacity"
              rules={[{ required: true, type: 'number', min: 1 }]}
            >
              <InputNumber min={1} style={{ width: '100%' }} />
            </Form.Item>

            <Form.Item
              name="cancellationFee"
              label="Cancellation Fee"
              rules={[{ required: true, type: 'number', min: 0 }]}
            >
              <InputNumber min={0} style={{ width: '100%' }} prefix="$" />
            </Form.Item>

            <Form.Item
              name="ticketPrice"
              label="Ticket Price"
              rules={[{ required: true, type: 'number', min: 0 }]}
            >
              <InputNumber min={0} style={{ width: '100%' }} prefix="$" />
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit">
                Create Event
              </Button>
            </Form.Item>
          </Form>
        </TabPane>
        <TabPane tab="Manage Events" key="2">
          <Table dataSource={events} columns={columns} rowKey="id" />
        </TabPane>
      </Tabs>
      <Modal
        title="Edit Event"
        open={!!editingEvent}
        onCancel={() => setEditingEvent(null)}
        footer={[
          <Button key="back" onClick={() => setEditingEvent(null)}>Cancel</Button>,
          <Button
            key="submit"
            type="primary"
            form="eventEditForm"
            htmlType="submit"
          >
            Update Event
          </Button>,
        ]}
      >
        <Form
          form={form}
          id="eventEditForm"
          onFinish={(values) => handleUpdate(editingEvent.id, values)}
          layout="vertical"
        >
          <Form.Item
            name="eventName"
            label="Event Name"
            rules={[{ required: true }]}
          >
            <Input placeholder="Enter the event name" />
          </Form.Item>

          <Form.Item
            name="dateTime"
            label="Event Date and Time"
            rules={[{ required: true }]}
          >
            <DatePicker showTime />
          </Form.Item>

          <Form.Item
            name="venue"
            label="Venue"
            rules={[{ required: true }]}
          >
            <Input placeholder="Enter the event venue" />
          </Form.Item>

          <Form.Item
            name="capacity"
            label="Capacity"
            rules={[{ required: true, type: 'number', min: 1 }]}
          >
            <InputNumber min={1} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="cancellationFee"
            label="Cancellation Fee"
            rules={[{ required: true, type: 'number', min: 0 }]}
          >
            <InputNumber min={0} style={{ width: '100%' }} prefix="$" />
          </Form.Item>

          <Form.Item
            name="ticketPrice"
            label="Ticket Price"
            rules={[{ required: true, type: 'number', min: 0 }]}
          >
            <InputNumber min={0} style={{ width: '100%' }} prefix="$" />
          </Form.Item>
        </Form>
      </Modal>

    </>
  );
};