import React,{ Component, Fragment } from 'react';
import {Route, Switch} from 'react-router-dom';
import './styles/App.css';
import StartPage from './components/auth/StartPage';
import RegisterPage from './components/auth/RegisterPage';
import LoginPage from './components/auth/LoginPage';
import ErrorPage from './components/common/ErrorPage';

import { ToastContainer, toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';
import { ToastComponent } from './components/common';
import userService from './infrastructure/userService';

class App extends Component{
  constructor(props){
    super(props)

    this.onLogout = this.onLogout.bind(this);
  }

  onLogout() {
    this.props.logout();

    toast.success(<ToastComponent.SuccessToast text={`You have been successfully logged out.`} />, {
      position: toast.POSITION.TOP_RIGHT
    });

    this.props.history.push('/login');
  }

  render() {
    const loggedIn = userService.isTheUserLoggedIn();

    return (
      <Fragment>
        <ToastContainer transition={Zoom} closeButton={false} />
          <Switch>
            <Route exact path="/" component={StartPage} />
            {!loggedIn && <Route exact path="/register" component={RegisterPage} />}
            {!loggedIn && <Route exact path="/login" component={LoginPage} />}
            <Route exact path="/error" component={ErrorPage} />
            <Route component={ErrorPage} />
          </Switch>
      </Fragment>
    )
  }

}

export default App;
