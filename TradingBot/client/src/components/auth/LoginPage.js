import React, { useState } from "react";
import { toast } from "react-toastify";
import { ToastComponent } from "../common";
import "../../styles/FormPages.css";

import { requester } from "./../../infrastructure";
import { useHistory } from "react-router";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [touched, setTouched] = useState({
    username: false,
    password: false,
  });
  const history = useHistory();

  const onChangeHandler = (e) => {
    const stateKey = e.target.name;
    const stateValue = e.target.value;

    debugger;
    console.log("currentStateKey: ", stateValue);

    if (stateKey === "username") {
      setUsername(stateValue);
    } else if (stateKey === "password") {
      setPassword(stateValue);
    }
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();

    if (!canBeSubmitted()) {
      return;
    }

    requester
      .post("/login", { username, password }, (response) => {
        if (response.error) {
          debugger;
          console.log(" Incorrect credentials!");
          toast.error(
            <ToastComponent.ЕrrorToast text={" Incorrect credentials!"} />,
            {
              position: toast.POSITION.TOP_RIGHT,
            }
          );
        } else {
          saveToken(response);
          console.log("LoggedIn");

          toast.success(
            <ToastComponent.SuccessToast
              text={" You have successfully logged in!"}
            />,
            {
              position: toast.POSITION.TOP_RIGHT,
            }
          );

          history.push("/");
        }
      })
      .catch((err) => {
        localStorage.clear();
        console.log("Error Login");
        debugger;

        toast.error(<ToastComponent.ErrorToast text={"Info message"} />, {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
  };

  const saveToken = (response) => {
    const token = response.split(" ")[1];
    localStorage.setItem("token", token);
  };

  const canBeSubmitted = () => {
    const errors = validate(username, password);
    const isDisabled = Object.keys(errors).some((x) => errors[x]);
    return !isDisabled;
  };

  const handleBlur = (field) => (event) => {
    setTouched({ ...touched, [field]: true });
  };

  const validate = (username, password) => {
    return {
      username: username.length === 0,
      password: password.length === 0,
    };
  };

  const errors = validate(username, password);
  const isEnabled = !Object.keys(errors).some((x) => errors[x]);

  const shouldMarkError = (field) => {
    const hasError = errors[field];
    const shouldShow = touched[field];

    return hasError ? shouldShow : false;
  };

  return (
    <section className="pt-3">
      <div className="container login-form-content-section pb-4 ">
        <h1
          className="text-center font-weight-bold mt-4"
          style={{ margin: "1rem auto", paddingTop: "2rem" }}
        >
          Login
        </h1>
        <div className="hr-styles" style={{ width: "70%" }}></div>

        <form className="Login-form-container" onSubmit={onSubmitHandler}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              className={
                "form-control " + (shouldMarkError("username") ? "error" : "")
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
              <small id="usernameHelp" className="form-text alert alert-danger">
                Username is required!
              </small>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              className={
                "form-control " + (shouldMarkError("password") ? "error" : "")
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
              <small id="passwordHelp" className="form-text alert alert-danger">
                Password is required!
              </small>
            )}
          </div>

          <div className="text-center">
            <button disabled={!isEnabled} type="submit" className="">
              Login
            </button>
          </div>
        </form>
      </div>
    </section>
  );
};

export default LoginPage;
