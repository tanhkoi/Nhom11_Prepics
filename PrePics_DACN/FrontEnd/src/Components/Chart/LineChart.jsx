import React from 'react';
import { LineChart, Line, XAxis, YAxis } from 'recharts';

const data = [
  { x: 1, y: 2 },
  { x: 2, y: 5.5 },
  { x: 3, y: 2 },
  { x: 5, y: 8.5 },
  { x: 8, y: 1.5 },
  { x: 10, y: 5 },
];

const MyLineChart = () => {
  return (
    <LineChart width={1500} height={300} data={data}>
      <XAxis dataKey="x" />
      <YAxis />
      <Line type="monotone" dataKey="y" stroke="#8884d8" />
    </LineChart>
  );
};

export default MyLineChart;