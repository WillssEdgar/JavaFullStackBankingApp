// import { useEffect } from "react";
// import { useNavigate } from "react-router-dom";

import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

interface Account {
  id: string;
  accountName: string;
  accountNumber: string;
}
console.log(localStorage.getItem("id"));
const Dashboard: React.FC = () => {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const userId = localStorage.getItem("id");
        const response = await axios.get(
          `http://localhost:8080/users/accounts?userId=${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            params: { userId: userId },
          }
        );
        console.log("Response Data", response.data);
        setAccounts(response.data);
      } catch (error) {
        console.error("Error fetching accounts: ", error);
      }
    };
    if (token) {
      fetchAccounts();
    }
  }, [token]);
  const navigate = useNavigate();
  const handleAccountClick = (
    accountId: string,
    accountNumber: string,
    accountName: string
  ) => {
    localStorage.setItem("id", accountId);
    localStorage.setItem("accountNumber", accountNumber);
    localStorage.setItem("accountName", accountName);
    navigate("/Account");
  };

  return (
    <div>
      <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row justify-content-evenly">
          <div className="col-8 m-5 text-center">
            <div className="container rounded-4 mt-5 bg-light-subtle d-flex align-items-center justify-content-center vh-30">
              <div className="row justify-content-evenly">
                <div className="col p-4 text-center">
                  <h3>Accounts: </h3>
                  <div className="d-grid gap-2 col-12 d-md-block">
                    {accounts.map((account) => (
                      <button
                        key={account.id}
                        type="button"
                        className="btn btn-primary btn-lg m-3 d-flex flex-column align-items-start"
                        style={{ width: "100%" }}
                        onClick={() =>
                          handleAccountClick(
                            account.id,
                            account.accountNumber,
                            account.accountName
                          )
                        }
                      >
                        <div style={{ display: "inline-block" }}>
                          <h4
                            style={{
                              display: "inline-block",
                              marginRight: "5px",
                            }}
                          >
                            Account Name:
                          </h4>
                          <span>{account.accountName}</span>
                        </div>
                        <br />
                        <div style={{ display: "inline-block" }}>
                          <h4
                            style={{
                              display: "inline-block",
                              marginRight: "5px",
                            }}
                          >
                            Account Number:
                          </h4>
                          <span>{account.accountNumber}</span>
                        </div>
                      </button>
                    ))}
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
