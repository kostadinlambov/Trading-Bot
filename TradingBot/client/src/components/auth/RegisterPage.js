import React, { Fragment, useState } from 'react';
import { Form, FormGroup, Input } from 'reactstrap';
import { useHistory } from 'react-router';
import styled from 'styled-components';
import { requester } from './../../infrastructure';
import { toast } from 'react-toastify';
import ToastComponent from '../common/ToastComponent';

const RegisterPage = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
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

    if (stateKey === 'username') {
      setUsername(stateValue);
    } else if (stateKey === 'email') {
      setEmail(stateValue);
    } else if (stateKey === 'password') {
      setPassword(stateValue);
    } else if (stateKey === 'confirmPassword') {
      setConfirmPassword(stateValue);
    } else if (stateKey === 'firstName') {
      setFirstName(stateValue);
    } else if (stateKey === 'lastName') {
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
    };

    requester
      .post('/api/user/register', { ...payload }, (response) => {
        if (response.success === true) {
          toast.success(
            <ToastComponent.SuccessToast text={response.message} />,
            {
              position: toast.POSITION.TOP_RIGHT,
            },
          );
          history.push('/login');
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
      confirmPassword,
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
    confirmPassword,
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
    confirmPassword,
  );
  const isEnabled = !Object.keys(errors).some((x) => errors[x]);

  const shouldMarkError = (field) => {
    const hasError = errors[field];
    const shouldShow = touched[field];
    return hasError ? shouldShow : false;
  };

  return (
    <Fragment>
      <FormWrapper className="background-black">
        <StyledHeader>Register</StyledHeader>
        <StyledForm onSubmit={onSubmitHandler}>
          <FormElementsWrapper>
            <StyledSectionContainer>
              <StyledSection>
                <StyledFormGroup>
                  <StyledLabel htmlFor="username">Username</StyledLabel>
                  <StyledInput
                    type="text"
                    name="username"
                    id="username"
                    value={username}
                    placeholder="Enter username"
                    onChange={onChangeHandler}
                    onBlur={handleBlur('username')}
                    aria-describedby="usernameHelp"
                    className={
                      'form-control ' +
                      (shouldMarkError('username') ? 'error' : '')
                    }
                  />
                  {shouldMarkError('username') && (
                    <small
                      id="usernameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!username
                        ? 'Username is required!'
                        : 'Username should be at least 4 and maximum 16 characters long!'}
                    </small>
                  )}
                </StyledFormGroup>
                <StyledFormGroup>
                  <StyledLabel htmlFor="username">First Name</StyledLabel>
                  <StyledInput
                    type="text"
                    id="firstName"
                    name="firstName"
                    value={firstName}
                    onChange={onChangeHandler}
                    onBlur={handleBlur('firstName')}
                    aria-describedby="firstNameHelp"
                    placeholder="Enter first name"
                    className={
                      'form-control ' +
                      (shouldMarkError('firstName') ? 'error' : '')
                    }
                  />
                  {shouldMarkError('firstName') && (
                    <small
                      id="firstNameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!firstName
                        ? 'First Name is required!'
                        : 'First Name must start with a capital letter and contain only letters!'}
                    </small>
                  )}
                </StyledFormGroup>
                <StyledFormGroup>
                  <StyledLabel htmlFor="password">Password</StyledLabel>
                  <StyledInput
                    type="password"
                    className={
                      'form-control ' +
                      (shouldMarkError('password') ? 'error' : '')
                    }
                    id="password"
                    name="password"
                    value={password}
                    onChange={onChangeHandler}
                    onBlur={handleBlur('password')}
                    aria-describedby="passwordHelp"
                    placeholder="Enter password"
                  />
                  {shouldMarkError('password') && (
                    <small
                      id="passwordHelp"
                      className="form-text alert alert-danger"
                    >
                      {!password
                        ? 'Password is required!'
                        : 'Password should be at least 4 and maximum 16 characters long!'}
                    </small>
                  )}
                </StyledFormGroup>
              </StyledSection>
              <StyledSection>
                <StyledFormGroup>
                  <StyledLabel htmlFor="email">Email Address</StyledLabel>
                  <StyledInput
                    type="email"
                    className={
                      'form-control ' +
                      (shouldMarkError('email') ? 'error' : '')
                    }
                    id="email"
                    name="email"
                    value={email}
                    onChange={onChangeHandler}
                    onBlur={handleBlur('email')}
                    aria-describedby="emailHelp"
                    placeholder="Enter email"
                  />
                  {shouldMarkError('email') && (
                    <small
                      id="emailHelp"
                      className="form-text alert alert-danger"
                    >
                      {!email
                        ? 'Email is required!'
                        : 'Invalid e-mail address!'}
                    </small>
                  )}
                </StyledFormGroup>

                <StyledFormGroup>
                  <StyledLabel htmlFor="lastName">Last Name</StyledLabel>
                  <StyledInput
                    type="text"
                    className={
                      'form-control ' +
                      (shouldMarkError('lastName') ? 'error' : '')
                    }
                    id="lastName"
                    name="lastName"
                    value={lastName}
                    onChange={onChangeHandler}
                    onBlur={handleBlur('lastName')}
                    aria-describedby="lastNameHelp"
                    placeholder="Enter last name"
                  />
                  {shouldMarkError('lastName') && (
                    <small
                      id="lastNameHelp"
                      className="form-text alert alert-danger"
                    >
                      {!lastName
                        ? 'Last Name is required!'
                        : 'Last Name must start with a capital letter and contain only letters!'}
                    </small>
                  )}
                </StyledFormGroup>
                <StyledFormGroup>
                  <StyledLabel htmlFor="confirmPassword">
                    Confirm Password
                  </StyledLabel>
                  <StyledInput
                    type="password"
                    className={
                      'form-control ' +
                      (shouldMarkError('confirmPassword') ? 'error' : '')
                    }
                    id="confirmPassword"
                    name="confirmPassword"
                    value={confirmPassword}
                    onChange={onChangeHandler}
                    onBlur={handleBlur('confirmPassword')}
                    aria-describedby="confirmPasswordHelp"
                    placeholder="Confirm your password"
                  />
                  {shouldMarkError('confirmPassword') && (
                    <small
                      id="confirmPasswordHelp"
                      className="form-text alert alert-danger"
                    >
                      Passwords do not match!
                    </small>
                  )}
                </StyledFormGroup>
              </StyledSection>
            </StyledSectionContainer>
            <div className="text-align-center">
              <StyledButton disabled={!isEnabled} type="submit" className="">
                Register
              </StyledButton>
            </div>
          </FormElementsWrapper>
        </StyledForm>
      </FormWrapper>
    </Fragment>
  );
};

export default RegisterPage;

// Styled Components
const FormWrapper = styled.div`
  width: 80%;
  margin: auto;
  padding: 0 1rem 1rem;
  margin: 3rem auto;

  @media (max-width: 768px) {
    width: 100%;
  }
`;

const FormElementsWrapper = styled.div`
  width: 100%;
  margin: auto;
  position: relative;

  .text-align-center {
    text-align: center;
    margin-top: 2rem;
  }

  @media (max-width: 360px) {
    .text-align-center {
    }
  }
`;

const StyledSectionContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StyledSection = styled.section`
  flex: 1 0 10px;
  margin: 0 2rem;
`;

const StyledForm = styled(Form)`
  border-color: ${(props) => props.theme.cards.borderColor};
  background-color: ${(props) => props.theme.cards.backgroundColor};
  box-shadow: ${(props) => props.theme.cards.boxShadow};
  padding: 2.5rem 1rem 1.2rem;
  border-radius: 10px;
`;

const StyledButton = styled.button`
  background-color: ${(props) => props.theme.solidButton.backgroundColor};
  border-color: ${(props) => props.theme.solidButton.borderColor};
  color: ${(props) => props.theme.solidButton.color};
  padding: 0.2rem 1rem;
  font-weight: bold;
  outline: none;
  border-radius: 5px;
  border: none;
  text-align: right;

  :hover {
    cursor: pointer;
    background-color: ${(props) =>
      props.theme.solidButtonHover.backgroundColor};
    border-color: ${(props) => props.theme.solidButtonHover.borderColor};
    color: ${(props) => props.theme.solidButtonHover.color};
  }

  :disabled {
    opacity: 0.4;
  }
`;

const StyledHeader = styled.h1`
  color: var(--app-title-color, #ffa000);
  font-family: 'SfDistantGalaxy';
  font-size: 3rem;
  text-align: center;
  line-height: 1;
  margin-bottom: 1rem;
  font-weight: bold;

  @media (max-width: 1000px) {
    font-size: 4rem;
  }

  @media (max-width: 768px) {
    font-size: 3rem;
  }

  @media (max-width: 600px) {
    font-size: 2rem;
  }
`;

const StyledInput = styled(Input)`
  &.form-control {
    background-color: ${(props) => props.theme.input.backgroundColor};
    border-color: ${(props) => props.theme.input.borderColor};
    color: ${(props) => props.theme.input.color};
    width: 100%;
    padding: 0.125rem 0.75rem;
    margin: auto;
    line-height: 0.8;
  }

  &.error:focus {
    border-color: rgba(229, 103, 23, 0.8);
    box-shadow: 0 1px 1px rgba(229, 103, 23, 0.075) inset,
      0 0 10px rgba(229, 103, 23, 0.8);
    outline: 0 none;
  }

  &.error {
    border: 1px solid red;
  }

  &.error-text {
    color: red;
    position: absolute;
  }
`;

const StyledLabel = styled.label`
  text-align: left;
  font-size: 0.9rem;
`;

const StyledFormGroup = styled(FormGroup)`
  text-align: left;
  margin-bottom: 3.8rem;
  position: relative;
`;
