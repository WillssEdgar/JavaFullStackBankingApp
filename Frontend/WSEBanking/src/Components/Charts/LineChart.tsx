import React from "react";
import "chart.js/auto";
import { Line } from "react-chartjs-2";
import { ChartData } from "chart.js/auto";

interface Transaction {
  balance: number;
  transactionDate: Array<number>;
}

interface LineChartProps {
  data: {
    transactions: Transaction[];
  };
}

const LineChart: React.FC<LineChartProps> = ({ data }) => {
  let chartData: ChartData<"line", number[], string> = {
    labels: [],
    datasets: [],
  };
  const options = {
    scales: {
      x: {
        title: {
          display: true,
          text: "Date: YYYY-MM-DD",
          color: "black",
        },
        grid: {
          display: false,
        },
        ticks: {
          color: "black",
        },
      },
      y: {
        title: {
          display: true,
          text: "$ US Dollars",
          color: "black",
        },
        grid: {
          color: "black", // Y-axis grid color
        },
        ticks: {
          color: "black",
        },
      },
    },
    plugins: {
      legend: {
        labels: {
          color: "black", // Legend label color
        },
      },
      tooltip: {
        backgroundColor: "#black", // Tooltip background color
        bodyColor: "#000000", // Tooltip text color
      },
    },
    backgroundColor: "#333333", // Background color of the chart
  };

  if (data && data.transactions && data.transactions.length > 0) {
    const labels = data.transactions.map(
      (transaction) =>
        `${transaction.transactionDate[0]}-${transaction.transactionDate[1]}-${transaction.transactionDate[2]}`
    );

    const balances = data.transactions.map(
      (transaction) => transaction.balance
    );

    chartData = {
      labels: labels,
      datasets: [
        {
          label: "Data",
          data: balances,
          fill: false,
          borderColor: "#007FFF",
          tension: 0.1,
        },
      ],
    };
  }

  return (
    <div>
      <h2 className="text-dark">Transaction History: </h2>
      <Line data={chartData} options={options} />
    </div>
  );
};

export default LineChart;
