import React, { Fragment, useState } from "react";
import { toast } from "react-toastify";
import { ToastComponent } from "../common";
import { requester } from "./../../infrastructure";
import { useHistory } from "react-router";
import "../../styles/FormPages.css";

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [profilePicUrl, setProfilePicUrl] = useState("");
  const [touched, setTouched] = useState({
    username: false,
    email: false,
    password: false,
    confirmPassword: false,
    firstName: false,
    lastName: false,
  });

  const history = useHistory();

  const onChangeHandler = (e) => {
    const stateKey = e.target.name;
    const stateValue = e.target.value;

    if (stateKey === "username") {
      setUsername(stateValue);
    } else if (stateKey === "email") {
      setEmail(stateValue);
    } else if (stateKey === "password") {
      setPassword(stateValue);
    } else if (stateKey === "confirmPassword") {
      setConfirmPassword(stateValue);
    } else if (stateKey === "firstName") {
      setFirstName(stateValue);
    } else if (stateKey === "lastName") {
      setLastName(stateValue);
    }
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();

    if (!canBeSubmitted()) {
      return;
    }

    const payload = {
      username,
      email,
      password,
      confirmPassword,
      firstName,
      lastName,
      profilePicUrl,
    };

    requester
      .post("/api/user/register", { ...payload }, (response) => {
        if (response.success === true) {
          toast.success(
            <ToastComponent.SuccessToast text={response.message} />,
            {
              position: toast.POSITION.TOP_RIGHT,
            }
          );
          history.push("/login");
        } else {
          toast.error(<ToastComponent.ErrorToast text={response.message} />, {
            position: toast.POSITION.TOP_RIGHT,
          });
        }
      })
      .catch((err) => {
        toast.error(<ToastComponent.ErrorToast text={`${err.message}`} />, {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
  };

  const canBeSubmitted = () => {
    const errors = validate(
      username,
      email,
      firstName,
      lastName,
      password,
      confirmPassword
    );
    const isDisabled = Object.keys(errors).some((x) => errors[x]);
    return !isDisabled;
  };

  const handleBlur = (field) => (event) => {
    setTouched({ ...touched, [field]: true });
  };

  const validate = (
    username,
    email,
    firstName,
    lastName,
    password,
    confirmPassword
  ) => {
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    const firstLastNameRegex = /^[A-Z]([a-zA-Z]+)?$/;
    const testEmail = emailRegex.test(email);
    const testFirstName = firstLastNameRegex.test(firstName);
    const testLastName = firstLastNameRegex.test(lastName);
    return {
      username: username.length < 4 || username.length > 16,
      email: email.length === 0 || !testEmail,
      firstName: firstName.length === 0 || !testFirstName,
      lastName: lastName.length === 0 || !testLastName,
      password: password.length < 4 || password.length > 16,
      confirmPassword:
        confirmPassword.length === 0 || confirmPassword !== password,
    };
  };

  const errors = validate(
    username,
    email,
    firstName,
    lastName,
    password,
    confirmPassword
  );
  const isEnabled = !Object.keys(errors).some((x) => errors[x]);

  const shouldMarkError = (field) => {
    const hasError = errors[field];
    const shouldShow = touched[field];
    return hasError ? shouldShow : false;
  };

  return (
    <Fragment>
      <section className="pt-3">
        <div className="container register-form-content-section pb-4 ">
          <h1
            className="text-center font-weight-bold mt-4"
            style={{ margin: "1rem auto", paddingTop: "2rem" }}
          >
            Register
          </h1>
          <div className="hr-styles" style={{ width: "70%" }}></div>

          <form className="Register-form-container" onSubmit={onSubmitHandler}>
            <div className="section-container">
              <section className="left-section">
                <div className="form-group">
                  <label htmlFor="username">Username</label>
                  <input
                    type="text"
                    className={
                      "form-control " +
                      (shouldMarkError("username") ? "error" : "")
                    }
                    id="username"
                    name="username"
                    value={username}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("username")}
                    aria-describedby="usernameHelp"
                    placeholder="Enter username"
                  />
                  {shouldMarkError("username") && (
                    <small
                      id="usernameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!username
                        ? "Username is required!"
                        : "Username should be at least 4 and maximum 16 characters long!"}
                    </small>
                  )}
                </div>

                <div className="form-group">
                  <label htmlFor="firstName">First Name</label>
                  <input
                    type="text"
                    className={
                      "form-control " +
                      (shouldMarkError("firstName") ? "error" : "")
                    }
                    id="firstName"
                    name="firstName"
                    value={firstName}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("firstName")}
                    aria-describedby="firstNameHelp"
                    placeholder="Enter first name"
                  />
                  {shouldMarkError("firstName") && (
                    <small
                      id="firstNameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!firstName
                        ? "First Name is required!"
                        : "First Name must start with a capital letter and contain only letters!"}
                    </small>
                  )}
                </div>

                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <input
                    type="password"
                    className={
                      "form-control " +
                      (shouldMarkError("password") ? "error" : "")
                    }
                    id="password"
                    name="password"
                    value={password}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("password")}
                    aria-describedby="passwordHelp"
                    placeholder="Enter password"
                  />
                  {shouldMarkError("password") && (
                    <small
                      id="passwordHelp"
                      className="form-text alert alert-danger"
                    >
                      {!password
                        ? "Password is required!"
                        : "Password should be at least 4 and maximum 16 characters long!"}
                    </small>
                  )}
                </div>
              </section>

              <section className="right-section">
                <div className="form-group">
                  <label htmlFor="email">Email Address</label>
                  <input
                    type="email"
                    className={
                      "form-control " +
                      (shouldMarkError("email") ? "error" : "")
                    }
                    id="email"
                    name="email"
                    value={email}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("email")}
                    aria-describedby="emailHelp"
                    placeholder="Enter email"
                  />
                  {shouldMarkError("email") && (
                    <small
                      id="emailHelp"
                      className="form-text alert alert-danger"
                    >
                      {!email
                        ? "Email is required!"
                        : "Invalid e-mail address!"}
                    </small>
                  )}
                </div>

                <div className="form-group">
                  <label htmlFor="lastName">Last Name</label>
                  <input
                    type="text"
                    className={
                      "form-control " +
                      (shouldMarkError("lastName") ? "error" : "")
                    }
                    id="lastName"
                    name="lastName"
                    value={lastName}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("lastName")}
                    aria-describedby="lastNameHelp"
                    placeholder="Enter last name"
                  />
                  {shouldMarkError("lastName") && (
                    <small
                      id="lastNameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!lastName
                        ? "Last Name is required!"
                        : "Last Name must start with a capital letter and contain only letters!"}
                    </small>
                  )}
                </div>

                <div className="form-group">
                  <label htmlFor="confirmPassword">Confirm Password</label>
                  <input
                    type="password"
                    className={
                      "form-control " +
                      (shouldMarkError("confirmPassword") ? "error" : "")
                    }
                    id="confirmPassword"
                    name="confirmPassword"
                    value={confirmPassword}
                    onChange={onChangeHandler}
                    onBlur={handleBlur("confirmPassword")}
                    aria-describedby="confirmPasswordHelp"
                    placeholder="Confirm your password"
                  />
                  {shouldMarkError("confirmPassword") && (
                    <small
                      id="confirmPasswordHelp"
                      className="form-text alert alert-danger"
                    >
                      Passwords do not match!
                    </small>
                  )}
                </div>
              </section>
            </div>

            <div className="text-center">
              <button disabled={!isEnabled} type="submit" className="">
                Register
              </button>
            </div>
          </form>
        </div>
      </section>
    </Fragment>
  );
};

export default RegisterPage;
