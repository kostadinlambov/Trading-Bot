import { createChart } from 'lightweight-charts';
import Chart from '../common/Chart2';
import Chart3 from '../common/Chart3';

const LandingPage = () => {
  return (
    <>
      <div>Welcome to Tradin Bot</div>
      {/* <Chart /> */}
      <Chart3 />

      <h3>Settings</h3>

      <div id="settings">
        <input type="checkbox" /> RSI
        <input type="text" id="rsi_length" name="rsi_length" placeholder="14" />
        Overbought
        <input
          type="text"
          id="rsi_overbought"
          name="rsi_overbought"
          placeholder="70"
        />
        Oversold
        <input
          type="text"
          id="rsi_oversold"
          name="rsi_oversold"
          placeholder="30"
        />
      </div>
    </>
  );
};

export default LandingPage;
