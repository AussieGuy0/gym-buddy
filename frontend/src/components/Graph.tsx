import Plotly, { Config, Data, Layout } from "plotly.js-basic-dist"
import React, { useEffect, useRef } from "react"

export interface GraphProps {
  data: Data[];
  labels: GraphLabels;
}

export interface GraphLabels {
  title: string;
  xAxis: string;
  yAxis: string;
}

export const Graph: React.FC<GraphProps> = ({ data, labels }) => {
  const graphDom = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (graphDom.current !== null) {
      const layout: Partial<Layout> = {
        title: { text: labels.title },
        xaxis: { title: labels.xAxis },
        yaxis: { title: labels.yAxis },
      };

      const config: Partial<Config> = {
        displayModeBar: false,
      };
      Plotly.newPlot(graphDom.current, data, layout, config).catch((err) =>
        console.error(err)
      );
    }
  });
  return (
    <div className="graph-container">
      <div className="graph" ref={graphDom}></div>
    </div>
  );
};
