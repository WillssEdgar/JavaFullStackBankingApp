import axios, { AxiosResponse } from "axios";
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
  const [accountNameError, setAccountNameError] = useState("");
  const [accountTypeError, setAccountTypeError] = useState("");
  const [showAccountMenu, setShowAccountMenu] = useState(false);
  const [showTransferMenu, setShowTransferMenu] = useState(false);
  const [selectedAccountType, setSelectedAccountType] = useState("default");
  const [accounts, setAccounts] = useState<Account[]>([]);
  const token = localStorage.getItem("token");
  const accNum = localStorage.getItem("accountNumber");
  const usaid = parseInt(localStorage.getItem("user_id") || "0", 10);
  const [selectedFromAccount, setSelectedFromAccount] = useState("");
  const [selectedToAccount, setSelectedToAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [transferError, setTransferError] = useState("");

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
    setShowAccountMenu((prev: any) => !prev);
  };

  const handleAddAccount = async () => {
    if (!newAccountName.trim()) {
      setAccountNameError("Account name is required.");
      return;
    }
    if (selectedAccountType == "default") {
      setAccountTypeError("Account type is required.");
      return;
    }

    try {
      const response: AxiosResponse<{ token: string }> = await axios.post(
        "http://localhost:8080/accounts/addNewAccount",
        {
          accountName: newAccountName,
          accountType: selectedAccountType,
          userId: usaid,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setAccountNameError("");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Registration failed:", error.response?.data);
      } else {
        console.error(
          "An unexpected error occurred during registration:",
          error
        );
      }
    }
  };
  const handleTransferMenuClick = () => {
    console.log("Before toggle:", showTransferMenu); // Log the current state
    setShowTransferMenu((prev: any) => !prev);
    console.log("After toggle:", showTransferMenu); // Log the updated state
  };
  const handleTransferMoney = async () => {
    if (!selectedFromAccount) {
      setTransferError("Please select a 'From' Account.");
      return;
    }

    if (!selectedToAccount) {
      setTransferError("Please select a 'To' Account.");
      return;
    }

    const parsedAmount = parseFloat(amount);
    if (isNaN(parsedAmount) || parsedAmount <= 0) {
      setTransferError("Please enter a valid positive amount.");
      return;
    }

    try {
      const response: AxiosResponse<{ token: string }> = await axios.post(
        "http://localhost:8080/accounts/transactions/transfer",
        {
          fromAccount: selectedFromAccount,
          toAccount: selectedToAccount,
          amount: amount,
          userId: usaid,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setTransferError("");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Transfer failed:", error.response?.data);
      } else {
        console.error(
          "An unexpected error occurred during registration:",
          error
        );
      }
    }
    // Reset form fields after transfer
    setSelectedFromAccount("");
    setSelectedToAccount("");
    setAmount("");
  };

  return (
    <div className="container d-flex align-items-center justify-content-center vh-100">
      <div className="row justify-content-evenly">
        <div className="col-8 m-5 text-center">
          <div className="container rounded-4 mt-5 bg-light-subtle p-3">
            <h3 className="mb-4 text-light">Accounts</h3>
            <div className="row justify-content-evenly">
              {accounts.map(
                (account: {
                  id: string;
                  accountName: string;
                  accountNumber: string;
                }) => (
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
                )
              )}
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
                  onChange={(e: { target: { value: any } }) =>
                    setNewAccountName(e.target.value)
                  }
                  placeholder="Enter Name"
                  className="form-control mb-3"
                />
                {accountNameError && (
                  <p className="text-danger">{accountNameError}</p>
                )}
                <div className="form-check mb-3">
                  <h5 className="mb-3">Account Type:</h5>
                  <input
                    className="form-check-input"
                    type="radio"
                    name="accountType"
                    id="checkingRadio"
                    value="CHECKINGS"
                    checked={selectedAccountType === "CHECKINGS"}
                    onChange={(e: { target: { value: any } }) => {
                      setSelectedAccountType(e.target.value);
                      setAccountTypeError("");
                    }}
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
                    value="SAVINGS"
                    checked={selectedAccountType === "SAVINGS"}
                    onChange={(e: { target: { value: any } }) => {
                      setSelectedAccountType(e.target.value);
                      setAccountTypeError("");
                    }}
                  />
                  <label className="form-check-label" htmlFor="savingsRadio">
                    Savings Account
                  </label>
                </div>
                {accountTypeError && (
                  <p className="text-danger">{accountTypeError}</p>
                )}
                <button
                  type="button"
                  className="btn btn-primary btn-lg"
                  onClick={handleAddAccount}
                >
                  Confirm New Account
                </button>
              </div>
            )}
            <button
              type="button"
              className="btn btn-primary btn-lg m-3"
              onClick={handleTransferMenuClick}
            >
              Transfer Money
            </button>
            {showTransferMenu && (
              <div className="card p-3">
                <div className="form-group">
                  <label htmlFor="fromAccount">From Account:</label>
                  <select
                    className="form-control"
                    id="fromAccount"
                    value={selectedFromAccount}
                    onChange={(e: { target: { value: any } }) =>
                      setSelectedFromAccount(e.target.value)
                    }
                  >
                    <option value="">Select Account</option>
                    {accounts.map(
                      (account: {
                        id: any;
                        accountNumber: any;
                        accountName: any;
                      }) => (
                        <option key={account.id} value={account.accountNumber}>
                          {account.accountName} - {account.accountNumber}
                        </option>
                      )
                    )}
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="toAccount">To Account:</label>
                  <select
                    className="form-control"
                    id="toAccount"
                    value={selectedToAccount}
                    onChange={(e: { target: { value: any } }) =>
                      setSelectedToAccount(e.target.value)
                    }
                  >
                    <option value="">Select Account</option>
                    {accounts.map(
                      (account: {
                        id: any;
                        accountNumber: any;
                        accountName: any;
                      }) => (
                        <option key={account.id} value={account.accountNumber}>
                          {account.accountName} - {account.accountNumber}
                        </option>
                      )
                    )}
                  </select>
                </div>
                <div className="form-group">
                  <label htmlFor="amount">Amount:</label>
                  <input
                    type="text"
                    className="form-control"
                    id="amount"
                    value={amount}
                    onChange={(e: { target: { value: any } }) =>
                      setAmount(e.target.value)
                    }
                  />
                </div>
                {transferError && (
                  <p className="text-danger">{transferError}</p>
                )}
                <button
                  type="button"
                  className="btn btn-primary btn-lg"
                  onClick={handleTransferMoney}
                >
                  Transfer Money
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
