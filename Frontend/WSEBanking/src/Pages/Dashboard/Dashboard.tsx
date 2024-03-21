import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

interface Account {
  id: string;
  user_id: string;
  accountName: string;
  accountNumber: string;
}

const Dashboard: React.FC = () => {
  const [newAccountName, setNewAccountName] = useState("");
  const [showAccountMenu, setShowAccountMenu] = useState(false);
  const [selectedAccountType, setSelectedAccountType] = useState("default");
  const [accounts, setAccounts] = useState<Account[]>([]);
  const token = localStorage.getItem("token");
  const accNum = localStorage.getItem("accountNumber");
  const usaid = localStorage.getItem("id");

  console.log("this is the account number in local storage right now", accNum);
  console.log("this is the user id in local storage right now", usaid);
  if (accNum) {
    localStorage.removeItem("accountNumber");
  }
  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const userId = localStorage.getItem("user_id");
        const response = await axios.get(
          `http://localhost:8080/users/accounts`,
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

  const handleAccountMenuClick = () => {
    console.log("Before toggle:", showAccountMenu); // Log the current state
    setShowAccountMenu((prev) => !prev);
    console.log("After toggle:", showAccountMenu); // Log the updated state
  };

  const handleAddAccount = () => {
    // Logic for adding a new account
  };

  return (
    <div className="container d-flex align-items-center justify-content-center vh-100">
      <div className="row justify-content-evenly">
        <div className="col-8 m-5 text-center">
          <div className="container rounded-4 mt-5 bg-light-subtle p-4">
            <h3 className="mb-4 text-light">Accounts</h3>
            <div className="row justify-content-evenly">
              {accounts.map((account) => (
                <div className="col-12 col-md-6" key={account.id}>
                  <div className="card mb-3">
                    <div className="card-body">
                      <h5 className="card-title">{account.accountName}</h5>
                      <p className="card-text">
                        Account Number: {account.accountNumber}
                      </p>
                      <button
                        type="button"
                        className="btn btn-primary btn-sm"
                        onClick={() =>
                          handleAccountClick(
                            account.id,
                            account.accountNumber,
                            account.accountName
                          )
                        }
                      >
                        View Details
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="col-8 p-4 rounded-4 bg-light-subtle text-center">
          <h3 className="text-light">Actions</h3>
          <div className="d-grid gap-2 d-md-block mt-4">
            <button
              type="button"
              className="btn btn-primary btn-lg m-3"
              onClick={handleAccountMenuClick}
            >
              Add Account
            </button>
            {showAccountMenu && (
              <div className="card p-3">
                <h4 className="mb-3">Add Account</h4>
                <label htmlFor="accountName" className="form-label">
                  Account Name
                </label>
                <input
                  type="text"
                  id="accountName"
                  value={newAccountName}
                  onChange={(e) => setNewAccountName(e.target.value)}
                  placeholder="Enter Name"
                  className="form-control mb-3"
                />
                <div className="form-check mb-3">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="accountType"
                    id="checkingRadio"
                    value="checking"
                    checked={selectedAccountType === "checking"}
                    onChange={(e) => setSelectedAccountType(e.target.value)}
                  />
                  <label className="form-check-label" htmlFor="checkingRadio">
                    Checking Account
                  </label>
                </div>
                <div className="form-check mb-3">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="accountType"
                    id="savingsRadio"
                    value="savings"
                    checked={selectedAccountType === "savings"}
                    onChange={(e) => setSelectedAccountType(e.target.value)}
                  />
                  <label className="form-check-label" htmlFor="savingsRadio">
                    Savings Account
                  </label>
                </div>
                <button
                  type="button"
                  className="btn btn-primary btn-lg"
                  onClick={handleAddAccount}
                >
                  Confirm New Account
                </button>
              </div>
            )}
            <button type="button" className="btn btn-primary btn-lg m-3">
              Transfer Money
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
