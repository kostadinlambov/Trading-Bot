import React, { Suspense, useState } from "react";
import { Route, Switch } from "react-router-dom";
import LandingPage from "./components/auth/LandingPage";
import RegisterPage from "./components/auth/RegisterPage";
import LoginPage from "./components/auth/LoginPage";
import ErrorPage from "./components/common/ErrorPage";
import NavbarComponent from "./components/layout/Navbar";
import Footer from "./components/layout/Footer";
import GlobalStyle from "./components/styledComponents/GlobalStyles";
import { ThemeProvider } from "styled-components";
import { light, dark } from "./components/styledComponents/theme";
import { Layout } from "./components/layout/Layout";
import { Loading } from "./components/layout/Loading";
import styled from "styled-components";

function App() {
  const localStorageTheme = JSON.parse(localStorage.getItem("theme")) || dark;
  debugger;
  const [currentTheme, setCurrentTheme] = useState(localStorageTheme);

  localStorage.setItem("theme", JSON.stringify(currentTheme));
  debugger;
  const handleClick = () => {
    debugger;
    setCurrentTheme(currentTheme === light ? dark : light);
    debugger;
    localStorage.setItem("theme", JSON.stringify(currentTheme));
  };

  document.body.style.backgroundColor = currentTheme.backgroundColor;

  return (
    <AppContainer>
      <ThemeProvider theme={currentTheme}>
        <NavbarComponent changeTheme={handleClick} />
        <Layout>
          <Suspense fallback={<Loading />}>
            <Switch>
              <Route exact path="/" component={LandingPage} />
              <Route exact path="/register" component={RegisterPage} />
              <Route exact path="/login" component={LoginPage} />
              <Route exact path="/error" component={ErrorPage} />
              <Route component={ErrorPage} />
            </Switch>
          </Suspense>
        </Layout>
        <Footer />
        <GlobalStyle />
      </ThemeProvider>
    </AppContainer>
  );
}

export default App;

const AppContainer = styled.div`
  text-align: center;
  background-color: #eee;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 5rem;
`;
