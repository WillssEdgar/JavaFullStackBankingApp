import { useState } from "react";
import { Link } from "react-router-dom";

function Nav() {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(
    () => !!localStorage.getItem("token")
  );

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false); // Update isLoggedIn state after logout
  };

  // setIsLoggedIn(!!localStorage.getItem("token"));
  return (
    <div>
      <nav className="navbar navbar-expand-lg fixed-top bg-body-tertiary font-monospace">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">
            <h2 className="font-monospace"> WSE Banking </h2>
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className="collapse navbar-collapse justify-content-end"
            id="navbarNav"
          >
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link
                  className="nav-link active border-end border-white "
                  aria-current="page"
                  to="/"
                >
                  <h5>Home</h5>
                </Link>
              </li>

              {isLoggedIn ? (
                <>
                  <li className="nav-item">
                    <Link
                      className="nav-link active border-end border-white"
                      aria-current="page"
                      to="/Dashboard"
                    >
                      <h5>Dashboard</h5>
                    </Link>
                  </li>
                  <li className="nav-item">
                    <a
                      className="nav-link active border-end border-white"
                      aria-current="page"
                      href="/Login_Create"
                      onClick={handleLogout}
                    >
                      <h5>Logout</h5>
                    </a>
                  </li>
                </>
              ) : (
                <li className="nav-item">
                  <Link
                    className="nav-link active border-end border-white"
                    aria-current="page"
                    to="/Login_Create"
                  >
                    <h5>Create/Login</h5>
                  </Link>
                </li>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Nav;
