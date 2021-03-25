import React, { useState } from 'react';
import { NavLink as RRNavLink } from 'react-router-dom';
import LogoutButton from '../common/LogoutButton';
import styled from 'styled-components';
import { Collapse, Navbar, NavbarToggler, Nav, NavItem, NavLink } from 'reactstrap';

const NavbarComponent = (props) => {
	const [isOpen, setIsOpen] = useState(false);

	const toggle = () => setIsOpen(!isOpen);

	return (
		<Styles>
			<Navbar expand="md">
				<StyledNavbarBrand className="theme-toggler" onClick={props.changeTheme}>
					Trading Bot
				</StyledNavbarBrand>
				<NavbarToggler onClick={toggle} />
				<Collapse isOpen={isOpen} navbar>
					<Nav className="ml-auto" navbar>
						<NavItem>
							<NavLink tag={RRNavLink} exact to="/register" className="navbar">
								Register
							</NavLink>
						</NavItem>
						<NavItem>
							<NavLink tag={RRNavLink} exact to="/login" className="navbar">
								Login
							</NavLink>
						</NavItem>
						<NavItem>
							<NavLink exact tag={RRNavLink} to="/" className="navbar">
								<LogoutButton />
							</NavLink>
						</NavItem>
					</Nav>
				</Collapse>
			</Navbar>
		</Styles>
	);
};

export default NavbarComponent;

// Styled Components
const Styles = styled.div`
	position: fixed;
	left: 0;
	right: 0;
	top: 0;
	z-index: 5;
	.navbar {
		background-color: ${(props) => props.theme.appBar.backgroundColor};
		border-color: ${(props) => props.theme.appBar.borderColor};
		color: ${(props) => props.theme.appBar.color};

		&:hover {
			color: var(--app-active-color, #ffa000);
		}

		&.active {
			color: var(--app-active-color, #ffa000);
		}
	}

	.navbar-nav {
		align-items: center;
		justify-content: center;
		font-size: 1.2rem;
	}

	.bg-navcolor {
		color: #4bd5ee;

		&:hover {
			color: white;
		}
	}

	.navbar-toggler-icon {
		color: #4bd5ee;
		background-color: #4bd5ee;
	}
`;

const StyledNavbarBrand = styled.div`
	&.theme-toggler {
		background-color: ${(props) => props.theme.appBar.backgroundColor};
		border-color: ${(props) => props.theme.appBar.borderColor};
		color: var(--app-active-color, #ffa000);
		font-family: 'SfDistantGalaxy';
		font-size: 2rem;
	}

	&:hover {
		cursor: pointer;
	}
`;
