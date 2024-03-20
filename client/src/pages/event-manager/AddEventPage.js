import React from 'react';
import { Form, Input, Button, DatePicker, InputNumber, message } from 'antd';
import { CalendarOutlined, EnvironmentOutlined, UserOutlined, InfoCircleOutlined, DollarOutlined } from '@ant-design/icons';
import moment from 'moment';

const { RangePicker } = DatePicker;

export const AddEventPage = () => {
  const [form] = Form.useForm();

  const onFinish = (values) => {
    console.log('Received values of form: ', values);
    message.success('Event successfully created!');
    // TO-DO: Integrate with endpoint to create an event.
  };

  const validateMessages = {
    required: '${label} is required!',
    types: {
      number: '${label} is not a valid number!',
    },
  };

  return (
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
      style={{ margin: '30px '}}
    >
      <Form.Item
        name="eventName"
        label="Event Name"
        rules={[{ required: true }]}
      >
        <Input placeholder="Enter the event name" />
      </Form.Item>
      
      <Form.Item
        name="description"
        label="Description"
        rules={[{ required: true }]}
      >
        <Input.TextArea rows={4} placeholder="Enter a description of the event" />
      </Form.Item>

      <Form.Item
        name="dateTime"
        label="Event Date and Time"
        rules={[{ required: true }]}
      >
        <RangePicker showTime />
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
  );
};
