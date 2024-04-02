// import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import home from "../../assets/home.png";
import login from "../../assets/login.png";
import dashboard from "../../assets/dashboard.png";

function Nav() {
  const navigate = useNavigate();
  const location = useLocation();
  const auth = location.state && location.state.token;

  console.log("Location State From Nav:", location.state);

  const handleLogout = () => {
    localStorage.clear;
    navigate(location.pathname, { replace: true });
    navigate("/Login_Create");
  };

  return (
    <div>
      <nav
        className="navbar fixed-top bg-dark font-monospace"
        style={{ height: "10%" }}
      >
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            <h2
              className="ms-5"
              style={{ color: "#007FFF", fontFamily: "sans-serif" }}
            >
              WESCU
            </h2>
          </a>

          <ul className="nav">
            <li className="nav-item">
              <Link
                className="nav-link active "
                aria-current="page"
                to={"/"}
                state={location.state}
              >
                <img
                  src={home}
                  className="img-fluid rounded-4 me-1"
                  style={{
                    width: "20px",
                    height: "20px",
                  }}
                  alt="home"
                />
                Home
              </Link>
            </li>

            {auth ? (
              <>
                <li className="nav-item">
                  <Link
                    className="nav-link active"
                    aria-current="page"
                    to={"/Dashboard"}
                    state={{
                      id: location.state.accountId,
                      user_Id: location.state.user_Id,
                      accountNumber: location.state.accountNumber,
                      accountName: location.state.accountName,
                      token: location.state.token,
                    }}
                  >
                    <img
                      src={dashboard}
                      className="img-fluid rounded-4 me-1"
                      style={{
                        width: "20px",
                        height: "20px",
                      }}
                      alt="dashboard"
                    />
                    Dashboard
                  </Link>
                </li>
                <li className="nav-item">
                  <a
                    className="nav-link active"
                    aria-current="page"
                    href="/Login_Create"
                    onClick={handleLogout}
                  >
                    <img
                      src={login}
                      className="img-fluid me-1 rounded-4"
                      style={{
                        width: "15px",
                        height: "15px",
                      }}
                      alt="logout"
                    />
                    Logout
                  </a>
                </li>
              </>
            ) : (
              <li className="nav-item">
                <Link
                  className="nav-link active"
                  aria-current="page"
                  to={"/Login_Create"}
                >
                  <img
                    src={login}
                    className="img-fluid me-1 rounded-4"
                    style={{
                      width: "15px",
                      height: "15px",
                    }}
                    alt="login"
                  />
                  Create/Login
                </Link>
              </li>
            )}
          </ul>
        </div>
      </nav>
    </div>
  );
}

export default Nav;
