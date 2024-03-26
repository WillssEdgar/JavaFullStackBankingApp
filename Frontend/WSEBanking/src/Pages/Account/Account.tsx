import axios from "axios";
import { useEffect, useState } from "react";
interface Account {
  id: string;
  accountName: string;
  accountNumber: string;
}

function Account() {
  const [withdrawalAmount, setWithdrawalAmount] = useState("");
  const [showWithdrawalMenu, setShowWithdrawalMenu] = useState(false);
  const [depositAmount, setDepositAmount] = useState("");

  const [showDepositMenu, setShowDepositMenu] = useState(false);

  const [account, setAccount] = useState<Account | null>(null);
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("user_id");
  const accountId = localStorage.getItem("id");
  const accountNumber = localStorage.getItem("accountNumber");

  console.log("this is the account number in local storage right now", userId);
  console.log("this is the user id in local storage right now", accountNumber);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/accounts/account`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            params: {
              accountNumber: accountNumber,
              userId: userId,
            },
          }
        );

        setAccount(response.data);
      } catch (error) {
        console.error("Error fetching accounts: ", error);
      }
    };
    if (token && userId) {
      fetchAccounts();
    }
  }, [token, userId]);

  const handWithdrawalClick = () => {
    setShowWithdrawalMenu((prev) => !prev);
  };

  const handleWithdrawalConfirm = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/accounts/transactions/withdrawal",
        {
          accountId: accountId,
          amount: withdrawalAmount,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      setShowWithdrawalMenu(false);
      setWithdrawalAmount("");
    } catch (error) {
      console.error("Error withdrawaling funds: ", error);
      // Handle error (e.g., display error message to the user)
    }
  };

  const handleDepositClick = () => {
    setShowDepositMenu((prev) => !prev);
  };
  const handleDepositConfirm = async () => {
    console.log("this is the userID", userId);
    console.log("this is the token", token);
    try {
      const response = await axios.post(
        "http://localhost:8080/accounts/transactions/deposit",
        {
          accountId: accountId,
          amount: depositAmount,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      setShowDepositMenu(false);
      setDepositAmount("");
    } catch (error) {
      console.error("Error depositing funds: ", error);
      // Handle error (e.g., display error message to the user)
    }
  };

  return (
    <div className="container " style={{ marginTop: "20%" }}>
      <div className="row justify-content-center">
        {account ? (
          <div className="col-md-6">
            <h3>Account Number: {account.accountNumber}</h3>
            <div className="card p-3">
              <h3 className="mb-3">Actions</h3>
              <button
                type="button"
                className="btn btn-primary btn-lg mb-3"
                onClick={handWithdrawalClick}
              >
                Withdrawal
              </button>
              {showWithdrawalMenu && (
                <div className="mb-3">
                  <input
                    type="number"
                    value={withdrawalAmount}
                    onChange={(e) => setWithdrawalAmount(e.target.value)}
                    placeholder="Enter Withdrawal Amount"
                    className="form-control mb-2"
                  />
                  <button
                    type="button"
                    className="btn btn-primary"
                    onClick={handleWithdrawalConfirm}
                  >
                    Confirm Withdrawal
                  </button>
                </div>
              )}

              <button
                type="button"
                className="btn btn-primary btn-lg mb-3"
                onClick={handleDepositClick}
              >
                Deposit
              </button>
              {showDepositMenu && (
                <div className="mb-3">
                  <input
                    type="number"
                    value={depositAmount}
                    onChange={(e) => setDepositAmount(e.target.value)}
                    placeholder="Enter Deposit Amount"
                    className="form-control mb-2"
                  />
                  <button
                    type="button"
                    className="btn btn-primary"
                    onClick={handleDepositConfirm}
                  >
                    Confirm Deposit
                  </button>
                </div>
              )}
            </div>
          </div>
        ) : (
          <h3>Loading Content...</h3>
        )}
      </div>
    </div>
  );
}

export default Account;
