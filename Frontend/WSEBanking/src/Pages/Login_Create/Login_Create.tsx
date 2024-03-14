import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import "./Login_Create.css";
import axios, { AxiosResponse } from "axios";

// interface AutheResponse {
//   token: string;
// }

function Login_Create() {
  const navigate = useNavigate();

  const handleLoginSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const form = event.target as HTMLFormElement;
    const emailInput = form.elements.namedItem("email") as HTMLInputElement;
    const passwordInput = form.elements.namedItem(
      "password"
    ) as HTMLInputElement;

    const email = emailInput.value;
    const password = passwordInput.value;

    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });
      const token = response.data.token;
      localStorage.setItem("token", token);

      console.log("Login successful:", response.data);
      console.log("Token: ", token);

      navigate("/Dashboard");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Login failed:", error.response?.data);
      } else {
        console.error("An unexpected error occurred during login:", error);
      }
    }
  };

  const handleRegistrationSubmit = async (
    event: FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();

    const form = event.target as HTMLFormElement;
    const firstNameInput = form.elements.namedItem(
      "firstName"
    ) as HTMLInputElement;
    const lastNameInput = form.elements.namedItem(
      "lastName"
    ) as HTMLInputElement;
    const usernameInput = form.elements.namedItem(
      "username"
    ) as HTMLInputElement;
    const emailInput = form.elements.namedItem("email") as HTMLInputElement;
    const passwordInput = form.elements.namedItem(
      "password"
    ) as HTMLInputElement;

    const firstName = firstNameInput.value;
    const lastName = lastNameInput.value;
    const username = usernameInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;

    try {
      const response: AxiosResponse<{ token: string }> = await axios.post(
        "http://localhost:8080/register",
        {
          firstName,
          lastName,
          username,
          email,
          password,
        }
      );
      const token = response.data.token;
      localStorage.setItem("token", token);

      console.log("Registration successful:", response.data);
      navigate("/Dashboard");
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

  return (
    <div className="login_create">
      <div className="container  d-flex align-items-center justify-content-center vh-100">
        <div
          className="row bg-light-subtle rounded justify-content-evenly"
          style={{ width: "80%", height: "auto" }}
        >
          <div className="col-8 m-5">
            <h1> Login To Account</h1>
            <form className="loginForm" onSubmit={handleLoginSubmit}>
              <div className="mb-3">
                <label htmlFor="exampleInputEmail1" className="form-label">
                  Email address
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="exampleInputEmail1"
                  aria-describedby="emailHelp"
                  name="email"
                />
                <div id="emailHelp" className="form-text">
                  We'll never share your email with anyone else.
                </div>
              </div>
              <div className="mb-3">
                <label htmlFor="exampleInputPassword1" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  className="form-control"
                  id="exampleInputPassword1"
                  name="password"
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Submit
              </button>
            </form>
          </div>

          <div className="col-8 m-5">
            <h1>Create An Account</h1>
            <form className="row g-3" onSubmit={handleRegistrationSubmit}>
              <div className="col-md-6">
                <label htmlFor="firstname" className="form-label">
                  First Name
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="firstname"
                  name="firstName"
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="lastname" className="form-label">
                  Last Name
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="lastname"
                  name="lastName"
                />
              </div>
              <div className="col-12">
                <label htmlFor="inputEmail4" className="form-label">
                  Email
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="inputEmail4"
                  name="email"
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="username" className="form-label">
                  Username
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="username"
                  name="username"
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="inputPassword4" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  className="form-control"
                  id="inputPassword4"
                  name="password"
                />
              </div>
              <div className="col-12">
                <button type="submit" className="btn btn-primary">
                  Sign in
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login_Create;
