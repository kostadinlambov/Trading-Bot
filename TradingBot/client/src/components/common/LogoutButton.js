import React from "react";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";

export default function LogoutButton() {
  const onLogoutHandler = () => {
    const localStorageTheme = JSON.parse(localStorage.getItem("theme"));
    localStorage.clear();
    localStorage.setItem("theme", JSON.stringify(localStorageTheme));
  };

  return (
    <StyledButton onClick={onLogoutHandler}>
      <FontAwesomeIcon icon={faSignOutAlt} />
    </StyledButton>
  );
}

// Styled Components
const StyledButton = styled.div`
  padding: 0.1rem 0.5rem;
  border-radius: 5px;
  outline: none;
  border: none;
  font-size: 1.4rem;
  background-color: ${(props) => props.theme.outlinedButton.backgroundColor};
  border-color: ${(props) => props.theme.outlinedButton.borderColor};
  color: ${(props) => props.theme.outlinedButton.color};
`;
