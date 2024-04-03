import axios, { AxiosResponse } from "axios";
import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import "./Dashboard.css";

interface Account {
  id: string;
  user_id: string;
  accountName: string;
  accountNumber: string;
  balance: string;
}

const Dashboard: React.FC = () => {
  const newAccountNameRef = useRef<HTMLInputElement>(null);
  const checkingRadioRef = useRef<HTMLInputElement>(null);
  const savingsRadioRef = useRef<HTMLInputElement>(null);
  const selectedFromAccountRef = useRef<HTMLSelectElement>(null);
  const selectedToAccountRef = useRef<HTMLSelectElement>(null);
  const amountRef = useRef<HTMLInputElement>(null);
  const [showAccountMenu, setShowAccountMenu] = useState(false);
  const [showTransferMenu, setShowTransferMenu] = useState(false);
  const [accounts, setAccounts] = useState<Account[]>([]);

  const location = useLocation();
  const user_Id = parseInt(location.state.user_Id);
  const token = location.state.token;

  console.log("Location from Dashboard", location.state);

  const [addAccountErrors, setAddAccountErrors] = useState<{
    [key: string]: string;
  }>({});
  const [transferErrors, setTransferErrors] = useState<{
    [key: string]: string;
  }>({});

  const checkingRadioSelected = checkingRadioRef.current?.checked;
  const savingsRadioSelected = savingsRadioRef.current?.checked;
  const selectedAccountTypeRef = checkingRadioSelected
    ? checkingRadioRef
    : savingsRadioSelected
    ? savingsRadioRef
    : null;

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/users/accounts`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            params: { userId: user_Id },
          }
        );
        console.log("response data ", response.data);
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
    userId: string,
    accountNumber: string,
    accountName: string
  ) => {
    const token = location.state.token;
    navigate("/Account", {
      state: {
        id: accountId,
        user_Id: userId,
        accountNumber: accountNumber,
        accountName: accountName,
        token: token,
      },
    });
  };

  const handleAccountMenuClick = () => {
    setShowAccountMenu((prev: any) => !prev);
  };

  const handleAddAccount = async () => {
    const newAccountName = newAccountNameRef.current?.value;
    const selectedAccountType =
      selectedAccountTypeRef?.current?.value || "default";
    const errors: { [key: string]: string } = {};

    if (!newAccountName || !newAccountName.trim()) {
      errors.newAccountName = "Account name is required.";
    }
    if (selectedAccountType == "default") {
      errors.selectedAccountType = "Account type is required.";
    }
    if (Object.keys(errors).length > 0) {
      setAddAccountErrors(errors);
      return;
    }

    try {
      const response: AxiosResponse<{ token: string }> = await axios.post(
        "http://localhost:8080/accounts/addNewAccount",
        {
          accountName: newAccountName,
          accountType: selectedAccountType,
          userId: user_Id,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      window.location.reload();
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
    const selectedFromAccount = selectedFromAccountRef.current?.value;
    const selectedToAccount = selectedToAccountRef.current?.value;
    const amount = amountRef.current?.value;

    const errors: { [key: string]: string } = {};

    if (!selectedFromAccount) {
      errors.selectedFromAccount = "Please select a 'From' Account.";
    }

    if (!selectedToAccount) {
      errors.selectedToAccount = "Please select a 'To' Account.";
    }

    if (!amount) {
      errors.amount = "Please enter a valid positive amount.";
    }

    if (Object.keys(errors).length > 0) {
      setTransferErrors(errors);
      return;
    }

    try {
      const response: AxiosResponse<{ token: string }> = await axios.post(
        "http://localhost:8080/accounts/transactions/transfer",
        {
          fromAccount: selectedFromAccount,
          toAccount: selectedToAccount,
          amount: amount,
          userId: user_Id,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      window.location.reload();
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
  };

  return (
    <div className="mainContainer">
      <div
        className="container d-flex align-items-center justify-content-center "
        style={{ marginTop: "10%", marginBottom: "10%" }}
      >
        <div className="row justify-content-evenly">
          <div className="col-12 m-5 text-center">
            <div
              className="container rounded-4 p-3 bg-dark bg-opacity-10 blur"
              style={{ boxShadow: "10px 10px 20px black", maxWidth: "80%" }}
            >
              <h1 className="mb-4 display-3 fw-bold">Accounts</h1>
              <div className="row justify-content-evenly">
                {accounts.map(
                  (account: {
                    id: string;
                    accountName: string;
                    accountNumber: string;
                    balance: string;
                  }) => (
                    <div className="col-12 col-md-6" key={account.id}>
                      <div
                        className="card border-0 mb-3 bg-dark bg-opacity-25 bg-gradient blur"
                        style={{
                          boxShadow: "10px 10px 15px black",
                        }}
                      >
                        <div className="card-body">
                          <h5 className="card-title text-light">
                            {account.accountName}
                          </h5>
                          <p className="card-text text-light">
                            Account Number: {account.accountNumber} <br />
                            Balance: ${parseFloat(account.balance).toFixed(2)}
                          </p>
                          <button
                            type="button"
                            className="btn btn-primary btn-sm opacity-75"
                            onClick={() =>
                              handleAccountClick(
                                account.id,
                                user_Id.toString(),
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
          <div
            className="col-12 p-4 rounded-4 text-center bg-dark bg-opacity-10 blur"
            style={{ boxShadow: "10px 10px 20px black" }}
          >
            <h1 className="display-3 fw-bold">Actions</h1>
            <div className="d-grid gap-3 d-md-block m-4">
              <button
                type="button"
                className="btn btn-primary btn-lg opacity-75 "
                onClick={handleAccountMenuClick}
                style={{ width: "100%" }}
              >
                Add Account
              </button>
              {showAccountMenu && (
                <div className="card border-0 bg-transparent mb-1 p-3">
                  <div className="form-floating mb-2">
                    <input
                      type="text"
                      ref={newAccountNameRef}
                      id="accountName"
                      className={"form-control border-dark"}
                      required
                    />
                    <label htmlFor="accountName">Account Name</label>
                    {addAccountErrors.newAccountName && (
                      <p className="text-danger">
                        {addAccountErrors.newAccountName}
                      </p>
                    )}
                  </div>
                  <div className="row justify-content-start m-2">
                    <div className="col" style={{ textAlign: "left" }}>
                      <h5 className="mb-3">Account Type:</h5>
                    </div>
                  </div>
                  <div className="form-check m-1">
                    <input
                      className="form-check-input border-dark"
                      ref={checkingRadioRef}
                      type="radio"
                      name="accountType"
                      id="checkingRadio"
                      value="CHECKINGS"
                    />
                    <label className="form-check-label" htmlFor="checkingRadio">
                      Checking Account
                    </label>
                  </div>
                  <div className="form-check m-1">
                    <input
                      className="form-check-input border-dark"
                      ref={savingsRadioRef}
                      type="radio"
                      name="accountType"
                      id="savingsRadio"
                      value="SAVINGS"
                    />
                    <label className="form-check-label" htmlFor="savingsRadio">
                      Savings Account
                    </label>
                  </div>
                  {addAccountErrors.selectedAccountType && (
                    <p className="text-danger">
                      {addAccountErrors.selectedAccountType}
                    </p>
                  )}
                  <button
                    type="button"
                    className="btn btn-primary btn-lg m-2 "
                    onClick={handleAddAccount}
                  >
                    Confirm New Account
                  </button>
                </div>
              )}
              <button
                type="button"
                className="btn btn-primary btn-lg mt-4 opacity-75"
                onClick={handleTransferMenuClick}
                style={{ width: "100%" }}
              >
                Transfer
              </button>
              {showTransferMenu && (
                <div className="card border-0 p-3">
                  <div className="form-floating m-1">
                    <select
                      className="form-select border-dark"
                      ref={selectedFromAccountRef}
                      id="fromAccount"
                    >
                      <option value="">Select Account</option>
                      {accounts.map(
                        (account: {
                          id: any;
                          accountNumber: any;
                          accountName: any;
                        }) => (
                          <option
                            key={account.id}
                            value={account.accountNumber}
                          >
                            {account.accountName} - {account.accountNumber}
                          </option>
                        )
                      )}
                    </select>
                    <label htmlFor="fromAccount ">From Account:</label>
                    {transferErrors.selectedFromAccount && (
                      <p className="text-danger" style={{ fontSize: "12px" }}>
                        {transferErrors.selectedFromAccount}
                      </p>
                    )}
                  </div>
                  <div className="form-floating m-1">
                    <select
                      className="form-select border-dark"
                      ref={selectedToAccountRef}
                      id="toAccount"
                    >
                      <option value="">Select Account</option>
                      {accounts.map(
                        (account: {
                          id: any;
                          accountNumber: any;
                          accountName: any;
                        }) => (
                          <option
                            key={account.id}
                            value={account.accountNumber}
                          >
                            {account.accountName} - {account.accountNumber}
                          </option>
                        )
                      )}
                    </select>
                    <label htmlFor="toAccount">To Account:</label>
                    {transferErrors.selectedToAccount && (
                      <p className="text-danger" style={{ fontSize: "12px" }}>
                        {transferErrors.selectedToAccount}
                      </p>
                    )}
                  </div>
                  <div className="form-floating m-1">
                    <input
                      type="text"
                      ref={amountRef}
                      className="form-control border-dark"
                      id="amount"
                    />
                    <label htmlFor="amount">Amount:</label>
                    {transferErrors.amount && (
                      <p className="text-danger" style={{ fontSize: "12px" }}>
                        {transferErrors.amount}
                      </p>
                    )}
                  </div>
                  <button
                    type="button"
                    className="btn btn-primary btn-lg mt-3"
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
    </div>
  );
};

export default Dashboard;
