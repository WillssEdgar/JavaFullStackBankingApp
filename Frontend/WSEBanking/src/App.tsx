import "bootstrap/dist/css/bootstrap.css";
import "./App.css";
import Nav from "./Components/Nav/Nav";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login_Create from "./Pages/Login_Create/Login_Create";
import Home from "./Pages/Home/Home";
import Dashboard from "./Pages/Dashboard/Dashboard";
import Account from "./Pages/Account/Account";

function App() {
  return (
    <div className="background">
      <BrowserRouter>
        <Nav />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/Login_Create" element={<Login_Create />} />
          <Route path="/Account" element={<Account />} />
          <Route path="/Dashboard" element={<Dashboard />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
