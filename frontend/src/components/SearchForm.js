import {Component} from "react";
import {withRouter} from "react-router-dom";

class SearchForm extends Component {

  constructor(props) {
    super(props);
    this.state = {value: ''};
    this.cambiarInput = this.cambiarInput.bind(this);
  }

  render() {

    return (
      <form Class="d-flex" onSubmit={event => {
        event.preventDefault()
        this.props.history.replace(`busqueda/${this.state.value}`)
      }}>
        <input Class="form-control me-2" type="search" placeholder="Search" aria-label="Search"
               onChange={this.cambiarInput}/>
        <button Class="btn btn-outline-success" type="submit">Search</button>
      </form>
    )
  }

  cambiarInput(event) {
    this.setState({value: event.target.value})
  }
}

export default withRouter(SearchForm);