import React from 'react';
import { Container } from 'reactstrap';
import styled from 'styled-components';

export const Layout = (props) => (
  <StyledContainer>{props.children}</StyledContainer>
);

const StyledContainer = styled(Container)`
  @media (max-width: 1000px) {
  }

  @media (max-width: 768px) {
  }

  @media (max-width: 600px) {
    &.container {
      padding-left: 0;
      padding-right: 0;
    }
  }
`;
