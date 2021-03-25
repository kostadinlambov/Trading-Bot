import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`

.app-footer {
  margin: auto auto 1.5rem;
} 

.app-primary-color {
  color:  var(--app-primary-color, rgb(22, 163, 254));
}

.app-secondary-color {
  color: var(--app-secondary-color, rgb(35, 201, 157));
}


.app-title-color{
  color: #FFA000
}

.app-button-primary {
  background-color: var(--app-primary-color, rgb(22, 163, 254));
  color: white;
}

.app-button-primary:hover {
  background: var(--app-secondary-color, rgb(35, 201, 157));
  color: #fff;
  border: 1px solid #fff;
  box-shadow: 0 0 14px 1px rgba(0, 0, 0, 0.3);
}

.app-button-secondary {
  background: var(--app-secondary-color, rgb(35, 201, 157));
  color: white;
}

.app-button-secondary:hover {
  background: var(--app-primary-color, rgb(22, 163, 254));
  color: #fff;
  border: 1px solid #fff;
  box-shadow: 0 0 14px 1px rgba(0, 0, 0, 0.3);
}

.error-text-label{
  color: red;
}

.error-text{
  color: red;
  position: absolute;
}

.alert{
  position: absolute;
  width: 100%;
  padding: 0.5rem 0.75rem;
}
`;

export default GlobalStyle;
