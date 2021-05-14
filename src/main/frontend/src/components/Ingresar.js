import {Button, Card, Container, Form} from "react-bootstrap";
import {useState} from "react";
import "../css/Auth.css"
import axios from "axios";

export const Ingresar = () => {
  const [usuario, setUsuario] = useState("")
  const [password, setPassword] = useState("")

  function handleSubmit(event) {
    event.preventDefault();

    let data = {
      usuario: usuario,
      password: password
    };

    axios.post("http://localhost:8080/login", data)
      .then((respuesta) => {
        //Escribir el token en cookies
      }).catch((respuesta) => {
        //Mostrar mensaje de error
    })
  }

  return <Container className="justify-content-md-center">
    <Card className={"text-center cardAuth"}>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          {/*<Alert variant="success" show={fueCreado}>¡Genial! Has creado el grupo {nombre} </Alert>*/}
          {/*<Alert variant="danger" show={noFueCreado}>¡Ups! Ocurrió un error creando el grupo {nombre}, intenta mas tarde </Alert>*/}
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