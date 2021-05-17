import {Alert, Button, Card, Container, Form} from "react-bootstrap";
import {useState} from "react";
import "../css/Auth.css"
import axios from "axios";
import {useHistory} from "react-router-dom";

export const Ingresar = () => {
  const history = useHistory();
  const [usuario, setUsuario] = useState("")
  const [password, setPassword] = useState("")
  const [noSePudoLoggear, setNoSePudoLoggear] = useState(false);

  function handleSubmit(event) {
    event.preventDefault();

    let data = {
      usuario: usuario,
      password: password
    };

    axios.post("http://localhost:8080/login", data)
      .then((respuesta) => {
        sessionStorage.setItem('accessToken', respuesta.data)
        sessionStorage.setItem('loggedUsername', usuario)
        history.replace("/")
      }).catch((respuesta) => {
        setNoSePudoLoggear(true)
    })
  }
  
  return <Container className="justify-content-md-center">
    <Card className={"text-center cardAuth"}>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Alert variant="danger" show={noSePudoLoggear}>Tu usuario o contraseña no son correctos </Alert>
          <Form.Group controlId="usuario" className="login-input">
            <Form.Label>Usuario</Form.Label>
            <Form.Control type="text" value={usuario}
                          onChange={event => setUsuario(event.target.value)}/>
          </Form.Group>

          <Form.Group controlId="password" className="login-input">
            <Form.Label>Contraseña</Form.Label>
            <Form.Control type="password" value={password}
                          onChange={event => setPassword(event.target.value)}/>
          </Form.Group>

          <Button variant="primary" type="submit">
            Ingresar
          </Button>
        </Form>
      </Card.Body>

    </Card>
  </Container>
};