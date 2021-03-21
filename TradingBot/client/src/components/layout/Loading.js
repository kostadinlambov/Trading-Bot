import React from 'react';
import styled from 'styled-components';

export const Loading = props => (
  <div>
    <StyledSpinner
      spinnerSize={props.spinnerSize}
      marginTop={props.marginTop}
    />
  </div>
);

// Styled Components
const StyledSpinner = styled.div.attrs({
  className: 'fa fa-spinner fa-spin',
})`
  &.fa-spinner {
    font-size: ${props => props.spinnerSize || '5rem'};
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: ${props => props.marginTop || '15rem'};
    color: ${props => props.theme.spinnerColor.color || 'black'};
  }
`;
