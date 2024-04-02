import { FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login_Create.css";
import axios, { AxiosResponse } from "axios";

function Login_Create() {
  const navigate = useNavigate();

  const [loginErrors, setLoginErrors] = useState<{ [key: string]: string }>({});
  const [registrationErrors, setRegistrationErrors] = useState<{
    [key: string]: string;
  }>({});

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
  const nameRegex = /^[a-zA-Z]+$/;
  const usernameRegex = /^[a-zA-Z0-9_]+$/;

  const handleLoginSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const form = event.target as HTMLFormElement;
    const emailInput = form.elements.namedItem("email") as HTMLInputElement;
    const passwordInput = form.elements.namedItem(
      "password"
    ) as HTMLInputElement;

    const email = emailInput.value;
    const password = passwordInput.value;

    const errors: { [key: string]: string } = {};

    if (!email || !emailRegex.test(email)) {
      errors.email = "Valid email is required.";
    }

    if (!password || !passwordRegex.test(password)) {
      errors.password =
        "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.";
    }

    if (Object.keys(errors).length > 0) {
      setLoginErrors(errors);
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });
      const token = response.data.token;
      const id = response.data.id;

      console.log("Login successful:", response.data);
      navigate("/Dashboard", { state: { user_Id: id, token: token } });
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

    const errors: { [key: string]: string } = {};

    if (!firstName || !nameRegex.test(firstName)) {
      errors.firstName = "Valid first name is required.";
    }

    if (!lastName || !nameRegex.test(lastName)) {
      errors.lastName = "Valid last name is required.";
    }

    if (!username || !usernameRegex.test(username)) {
      errors.username = "Valid username is required.";
    }

    if (!email || !emailRegex.test(email)) {
      errors.email = "Valid email is required.";
    }

    if (!password || !passwordRegex.test(password)) {
      errors.password =
        "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.";
    }

    if (Object.keys(errors).length > 0) {
      setRegistrationErrors(errors);
      return;
    }

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
  const loginErrorsPresent = Object.keys(loginErrors).length > 0;
  const registrationErrorsPresent = Object.keys(registrationErrors).length > 0;
  const marginTop =
    loginErrorsPresent || registrationErrorsPresent ? "25rem" : "10rem";
  const marginBottom =
    loginErrorsPresent || registrationErrorsPresent ? "20rem" : "10rem";

  return (
    <div
      className="container d-flex align-items-center justify-content-center vh-100 "
      style={{ marginTop, marginBottom }}
    >
      <div
        className="row bg-light-subtle rounded justify-content-evenly"
        style={{ width: "80%" }}
      >
        <div className="col-8 m-5">
          <h1> Login To Account</h1>
          <form className="loginForm " onSubmit={handleLoginSubmit}>
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
                required
              />
              {loginErrors.email && (
                <p className="text-danger">{loginErrors.email}</p>
              )}
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
                required
              />
              {loginErrors.password && (
                <p className="text-danger">{loginErrors.password}</p>
              )}
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
                required
              />
              {registrationErrors.firstName && (
                <p className="text-danger">{registrationErrors.firstName}</p>
              )}
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
                required
              />
              {registrationErrors.lastName && (
                <p className="text-danger">{registrationErrors.lastName}</p>
              )}
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
                required
              />
              {registrationErrors.email && (
                <p className="text-danger">{registrationErrors.email}</p>
              )}
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
                required
              />
              {registrationErrors.username && (
                <p className="text-danger">{registrationErrors.username}</p>
              )}
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
                required
              />
              {registrationErrors.password && (
                <p className="text-danger">{registrationErrors.password}</p>
              )}
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
  );
}

export default Login_Create;
