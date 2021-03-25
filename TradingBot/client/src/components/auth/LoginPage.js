import React, { useState } from 'react';
import { Form, FormGroup, Input } from 'reactstrap';
import { requester } from './../../infrastructure';
import { useHistory } from 'react-router';
import styled from 'styled-components';
import { toast } from 'react-toastify';
import ToastComponent from '../common/ToastComponent';

const LoginPage = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [touched, setTouched] = useState({
		username: false,
		password: false,
	});

	const history = useHistory();

	const onChangeHandler = (e) => {
		const stateKey = e.target.name;
		const stateValue = e.target.value;

		if (stateKey === 'username') {
			setUsername(stateValue);
		} else if (stateKey === 'password') {
			setPassword(stateValue);
		}
	};

	const onSubmitHandler = (e) => {
		e.preventDefault();

		if (!canBeSubmitted()) {
			return;
		}

		requester
			.post('/login', { username, password }, (response) => {
				if (response.error) {
					console.log(' Incorrect credentials!');
					toast.error(<ToastComponent.ЕrrorToast text={' Incorrect credentials!'} />, {
						position: toast.POSITION.TOP_RIGHT,
					});
				} else {
					saveToken(response);
					console.log('LoggedIn');

					toast.success(<ToastComponent.SuccessToast text={' You have successfully logged in!'} />, {
						position: toast.POSITION.TOP_RIGHT,
					});

					// const user = userService.getPayload();
					// userContext.logIn(user);

					history.push('/');
				}
			})
			.catch((err) => {
				localStorage.clear();
				console.log('Error Login');
				debugger;

				toast.error(<ToastComponent.ErrorToast text={'Info message'} />, {
					position: toast.POSITION.TOP_RIGHT,
				});
			});
	};

	const saveToken = (response) => {
		const token = response.split(' ')[1];
		localStorage.setItem('token', token);
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
		<>
			<FormWrapper className="background-black">
				<StyledHeader>Login</StyledHeader>
				<StyledForm onSubmit={onSubmitHandler}>
					<FormElementsWrapper>
						<div>
							<StyledFormGroup>
								<StyledLabel htmlFor="exampleUsername">Username</StyledLabel>
								<StyledInput
									type="text"
									name="username"
									id="exampleUsername"
									placeholder="Enter username"
									onChange={onChangeHandler}
									onBlur={handleBlur('username')}
									aria-describedby="usernameHelp"
									className={'form-control ' + (shouldMarkError('username') ? 'error' : '')}
								/>
								{shouldMarkError('username') && (
									<small id="usernameHelp" className="form-text alert alert-danger">
										Username is required!
									</small>
								)}
							</StyledFormGroup>
						</div>
						<div>
							<StyledFormGroup>
								<StyledLabel htmlFor="examplePassword">Password </StyledLabel>
								<StyledInput
									type="password"
									name="password"
									id="examplePassword"
									placeholder="********"
									onChange={onChangeHandler}
									onBlur={handleBlur('password')}
									aria-describedby="passwordHelp"
									className={'form-control ' + (shouldMarkError('password') ? 'error' : '')}
								/>
								{shouldMarkError('password') && (
									<small id="passwordHelp" className="form-text alert alert-danger">
										Password is required!
									</small>
								)}
							</StyledFormGroup>
						</div>
						<div className="text-align-right">
							<StyledButton disabled={!isEnabled} type="submit" className="">
								Login
							</StyledButton>
						</div>
					</FormElementsWrapper>
				</StyledForm>
			</FormWrapper>
		</>
	);
};

export default LoginPage;

// Styled Components
const FormWrapper = styled.div`
	width: 50%;
	margin: auto;
	padding: 0 1rem 1rem;
	margin: 3rem auto;
	@media (max-width: 1200px) {
		width: 60%;
		margin: 3rem auto;
	}

	@media (max-width: 768px) {
		width: 70%;
	}

	@media (max-width: 600px) {
		width: 80%;
		margin: 3rem auto;
	}
`;

const FormElementsWrapper = styled.div`
	width: 90%;
	margin: auto;
	position: relative;

	.text-align-right {
		text-align: right;
	}
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
		background-color: ${(props) => props.theme.solidButtonHover.backgroundColor};
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
		box-shadow: 0 1px 1px rgba(229, 103, 23, 0.075) inset, 0 0 10px rgba(229, 103, 23, 0.8);
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
	margin-bottom: 3rem;
`;
