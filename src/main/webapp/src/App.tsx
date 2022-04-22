import React, {useState} from 'react';
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';
import {Bar} from 'react-chartjs-2';
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
  const [latestTemperature, setLatestTemperature] = useState(0);
  const [coldestTemperature, setColdestTemperature] = useState(0);
  const [warmestTemperature, setWarmestTemperature] = useState(0);
  const [averageTemperature, setAverageTemperature] = useState(0);

  window.setInterval( () =>  {
    axios.get("http://localhost:8090/meteo-station/api/v1/meteo-data")
        .then(r => {
          setLatestTemperature(r.data.currentTemperature);
          setColdestTemperature(r.data.minTemperature);
          setWarmestTemperature(r.data.maxTemperature);
          setAverageTemperature(r.data.avgTemperature);
        })
        .catch(e => console.log(e));
  }, 5000);


  const labels = ['Temperatur Heute'];
  const data = {
    labels,
    datasets: [
      {
        label: 'Letzte',
        data: [latestTemperature],
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
