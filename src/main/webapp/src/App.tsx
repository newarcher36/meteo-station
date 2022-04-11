import React, {useEffect, useState} from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import axios from "axios";


ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);



export function App() {
  const [currentTemperature, setCurrentTemperature] = useState(0);
  const [coldestTemperature, setColdestTemperature] = useState(0);
  const [warmestTemperature, setWarmestTemperature] = useState(0);
  const [averageTemperature, setAverageTemperature] = useState(0);

  useEffect(() => {
    const res = axios.get("https://localhost:8090/meteo-station/api/v1/meteo-data");
    res.then();
    setCurrentTemperature(5);
    setColdestTemperature(-2);
    setWarmestTemperature(10);
    setAverageTemperature(6);
  }, []);

  const labels = ['Heute'];
  const data = {
    labels,
    datasets: [
      {
        label: 'Aktuelle Temperatur',
        data: [currentTemperature],
        backgroundColor: 'rgba(234,230,12,0.5)',
      },
      {
        label: 'Kältesten',
        data: [coldestTemperature],
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
      {
        label: 'Wärmesteten',
        data: [warmestTemperature],
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
      {
        label: 'Average',
        data: [averageTemperature],
        backgroundColor: 'rgba(59,235,53,0.5)',
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Bayerische Wetterstation',
      },
    },
  };

  return <Bar options={options} data={data} />;
}
