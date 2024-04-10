import React, { useEffect, useState } from 'react'
import { ArrowDownOutlined, ArrowUpOutlined } from '@ant-design/icons'
import { Card, Col, Modal, Row, Statistic } from 'antd'

const conicColors = {
  '0%': '#87d068',
  '50%': '#ffe58f',
  '100%': '#ffccc7'
}

export const EventStatistics = (props) => {
  const { visible, setVisible, selectedRecord, onClose } = props
  console.log(selectedRecord)
  const {
    eventName,
    dateTime,
    capacity,
    totalTicketsSold,
    totalRevenue,
    totalTicketsRefunded,
    eventCancelled,
    attendance
  } = selectedRecord

  const handleDaysLeft = () => {
    if (eventCancelled) return 'Cancelled'

    const daysLeft = Math.floor(
      (new Date(dateTime) - new Date()) / (1000 * 60 * 60 * 24)
    )

    if (daysLeft > 0) {
      return daysLeft
    } else if (daysLeft === 0) {
      return 'Ongoing'
    } else {
      return 'Ended'
    }
  }

  const capacityFilled =
    (totalTicketsSold / (capacity + totalTicketsSold)) * 100

  const totalNoShows = totalTicketsSold - attendance

  const hasEventCompleted = handleDaysLeft() === 'Ended' && !eventCancelled

  return (
    <Modal
      title={`Overview statistic of ${eventName}`}
      open={visible}
      footer={null}
      onCancel={() => {
        setVisible(false)
        onClose(null)
      }}
    >
      <Row gutter={[16, 16]}>
        <Col span={12}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Days Left to the Event"
              value={handleDaysLeft()}
            />
          </Card>
        </Col>
        <Col span={12}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Capacity Filled"
              value={capacityFilled}
              precision={2}
              valueStyle={{
                color: '#cf1322'
              }}
              suffix="%"
            />
          </Card>
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        <Col span={12}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Total Revenue"
              value={totalRevenue}
              precision={2}
              valueStyle={{
                color: '#3f8600'
              }}
              prefix="$"
            />
          </Card>
        </Col>
        <Col span={12}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Total Number of Refunds"
              value={totalTicketsRefunded}
              valueStyle={{
                color: '#cf1322'
              }}
            />
          </Card>
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        <Col span={24}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Total Attendees"
              value={hasEventCompleted ? attendance : 'N/A'}
            />
          </Card>
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        <Col span={24}>
          <Card bordered={false} style={{ margin: '5px' }}>
            <Statistic
              title="Total Number of No-Shows"
              value={hasEventCompleted ? totalNoShows : 'N/A'}
            />
          </Card>
        </Col>
      </Row>
    </Modal>
  )
}
