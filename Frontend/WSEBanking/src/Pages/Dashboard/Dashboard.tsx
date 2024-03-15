// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";

interface DashboardProps {}

const Dashboard: React.FC<DashboardProps> = () => {
  //const navigate = useNavigate();
  // const token = localStorage.getItem("token");
  // if (!token) {
  //   navigate("/Login_Create");
  //   return null;
  // }

  // useEffect(() => {
  //   const token = localStorage.getItem("token");
  //   if (!token) {
  //     localStorage.remove("token");
  //     navigate("/Login_Create");
  //   }
  // }, [navigate]);

  return (
    <div>
      <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row justify-content-evenly">
          <div className="col-8 m-5 text-center">
            <div className="container rounded-4 mt-5 bg-light-subtle d-flex align-items-center justify-content-center vh-30">
              <div className="row justify-content-evenly">
                <div className="col-8 p-4 text-center">
                  <h3>Accounts: </h3>
                  <div className="d-grid gap-2 d-md-block">
                    <button
                      type="button"
                      className="btn btn-primary btn-lg m-3"
                      style={{ width: "75%" }}
                    >
                      Account Name: Checkings <br /> Account: 12345678
                    </button>
                    <button
                      type="button"
                      className="btn btn-primary btn-lg m-3"
                      style={{ width: "75%" }}
                    >
                      Account Name: Savings <br /> Account: 87654321
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-8 p-4 rounded-4 bg-light-subtle text-center">
            <h3>Actions: </h3>
            <div className="d-grid gap-2 d-md-block">
              <button type="button" className="btn btn-primary btn-lg m-3">
                Add Account
              </button>
              <button type="button" className="btn btn-primary btn-lg m-3">
                Transfer Money
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
