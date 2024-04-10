import React, { useState, useEffect } from 'react'
import {
  Form,
  Input,
  Button,
  DatePicker,
  InputNumber,
  message,
  Typography,
  Tabs,
  Table,
  Modal,
  Tooltip
} from 'antd'
import {
  CloudDownloadOutlined,
  StopOutlined,
  EditOutlined,
  InfoCircleOutlined
} from '@ant-design/icons'

import {
  createEvent,
  getAllEvents,
  updateEvent,
  cancelEvent,
  exportEventDetails
} from '../../utils/api.js'
import moment from 'moment'
import { EventStatistics } from './EventStatistics.js'

const { Title } = Typography
const { TabPane } = Tabs

export const AddEventPage = () => {
  const [form] = Form.useForm()
  const [events, setEvents] = useState([])
  const [editingEvent, setEditingEvent] = useState(null)
  const [eventStatisticsVisible, setEventStatisticsVisible] = useState(false)
  const [selectedRecord, setSelectedRecord] = useState(null)

  const authUser = JSON.parse(localStorage.getItem('authUser'))

  const retrieveEvents = async () => {
    const fetchedEvents = await getAllEvents()

    setEvents(
      fetchedEvents.map((event) => ({
        ...event,
        dateTime: moment(event.dateTime)
      }))
    ) // Convert dateTime to moment
  }

  useEffect(() => {
    retrieveEvents()
  }, [])

  const onFinish = async (values) => {
    const requestBody = {
      event: {
        ...values,
        dateTime: values.dateTime.toISOString()
      },
      managerId: authUser.id
    }

    try {
      const response = await createEvent(requestBody)
      console.log(response)
      if (response && response.status == 201) {
        message.success(response.message)
        retrieveEvents()
        form.resetFields()
      } else {
        message.error('Failed to create event.')
      }
    } catch (error) {
      console.error(error)
      if (error.response && error.response.status === 400) {
        message.error(error.response.data.message)
      } else {
        message.error('An error occurred while creating the event.')
      }
    }
  }

  const handleDelete = (record) => {
    Modal.confirm({
      title: 'Are you sure you want to cancel this event?',
      content: 'This will cancel the event and initiate a refund process.',
      okText: 'Yes, cancel it',
      cancelText: 'No, keep it',
      onOk: async () => {
        try {
          const response = await cancelEvent(record.id)
          if (response && response.status === 200) {
            message.success('Event canceled successfully!')
            retrieveEvents()
          } else {
            message.error('Failed to cancel the event.')
          }
        } catch (error) {
          console.error('Error cancelling the event:', error)
          message.error('An error occurred while cancelling the event.')
        }
      }
    })
  }

  const handleEdit = (record) => {
    setEditingEvent(record)
    form.setFieldsValue({
      ...record,
      dateTime: moment(record.dateTime) // Ensure dateTime is set as a moment object for DatePicker
    })
  }

  const handleExport = async (eventId) => {
    try {
      /* To handle the downloading of the data */
      const response = await exportEventDetails(eventId)
      const blob = new Blob([response.data], { type: 'text/csv' })

      const url = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = url
      link.download = `event_details_${eventId}_${moment(new Date()).format(
        'DDMMYYYY'
      )}.csv`

      link.click()
      window.URL.revokeObjectURL(url)
    } catch (error) {
      console.log(error)
      message.error(
        'Something went wrong while exporting data. Try again later!'
      )
    }
  }

  const handleUpdate = async (eventId, values) => {
    const requestBody = {
      event: {
        ...values,
        dateTime: values.dateTime.toISOString()
      },
      managerId: authUser.id
    }

    try {
      const response = await updateEvent(eventId, requestBody)
      if (response && response.status === 201) {
        message.success('Event updated successfully!')

        retrieveEvents()
        setEditingEvent(null)
      } else {
        message.error('Update failed.')
      }
    } catch (error) {
      console.error(error)
      message.error('An error occurred while updating the event.')
    }
  }

  const isEditAndCancelDisabled = (event) => {
    return event.eventCancelled || moment().isSameOrAfter(event.dateTime, 'day')
  }

  const validateMessages = {
    required: '${label} is required!',
    types: {
      number: '${label} is not a valid number!'
    }
  }

  const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id', align: 'center' },
    {
      title: 'Event Name',
      dataIndex: 'eventName',
      key: 'eventName',
      align: 'left'
    },
    {
      title: 'Date and Time of Event',
      dataIndex: 'dateTime',
      key: 'dateTime',
      align: 'left',
      render: (text) => moment(text).format('Do MMMM YYYY, h:mm a')
    },
    { title: 'Venue', dataIndex: 'venue', key: 'venue', align: 'left' },
    {
      title: 'Capacity',
      dataIndex: 'capacity',
      key: 'capacity',
      align: 'center',
      render: (_, record) => record.capacity + record.totalTicketsSold
    },

    {
      title: 'Ticket Price ($)',
      dataIndex: 'ticketPrice',
      key: 'ticketPrice',
      align: 'center'
    },
    {
      title: 'Cancellation Fee ($)',
      dataIndex: 'cancellationFee',
      key: 'cancellationFee',
      align: 'center'
    },
    {
      title: 'Action',
      key: 'action',
      align: 'center',
      render: (_, record) => (
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button
            type="text"
            primary
            icon={<InfoCircleOutlined />}
            style={{ marginLeft: '10px' }}
            onClick={() => {
              setSelectedRecord(record)
              setEventStatisticsVisible(true)
            }}
          />
          <Tooltip
            title={
              isEditAndCancelDisabled(record)
                ? 'Unable to edit this event as this event has already started or is cancelled.'
                : 'Edit Event'
            }
          >
            <Button
              type="text"
              disabled={isEditAndCancelDisabled(record)}
              icon={<EditOutlined />}
              onClick={() => handleEdit(record)}
            />
          </Tooltip>
          <Tooltip
            title={
              isEditAndCancelDisabled(record)
                ? 'Unable to cancel this event as this event has already started or is cancelled.'
                : 'Edit Event'
            }
          >
            <Button
              disabled={isEditAndCancelDisabled(record)}
              type="text"
              danger
              icon={<StopOutlined />}
              style={{ marginLeft: '10px' }}
              onClick={() => handleDelete(record)}
            />
          </Tooltip>
          <Tooltip title="Export Event Details">
            <Button
              type="text"
              primary
              icon={<CloudDownloadOutlined />}
              style={{ marginLeft: '10px' }}
              onClick={() => handleExport(record.id)}
            />
          </Tooltip>
        </div>
      )
    }
  ]

  return (
    <>
      <Tabs defaultActiveKey="1" style={{ marginLeft: '20px' }}>
        <TabPane tab="Create Event" key="1">
          <Title level={2} style={{ marginLeft: '30px' }}>
            Create Event
          </Title>
          <Form
            form={form}
            name="event_form"
            onFinish={onFinish}
            validateMessages={validateMessages}
            layout="vertical"
            initialValues={{
              cancellationFee: 0.0,
              ticketPrice: 0.0
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

            <Form.Item name="venue" label="Venue" rules={[{ required: true }]}>
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
              label="Ticket Price ($)"
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
          {selectedRecord && (
            <EventStatistics
              visible={eventStatisticsVisible}
              setVisible={setEventStatisticsVisible}
              selectedRecord={selectedRecord}
              onClose={setSelectedRecord} // Default it back to null
            />
          )}
        </TabPane>
      </Tabs>
      <Modal
        title="Edit Event"
        open={!!editingEvent}
        onCancel={() => setEditingEvent(null)}
        footer={[
          <Button key="back" onClick={() => setEditingEvent(null)}>
            Cancel
          </Button>,
          <Button
            key="submit"
            type="primary"
            form="eventEditForm"
            htmlType="submit"
          >
            Update Event
          </Button>
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

          <Form.Item name="venue" label="Venue" rules={[{ required: true }]}>
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
  )
}
