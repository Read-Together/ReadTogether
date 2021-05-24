import { Alert, Button, Card, Form } from "react-bootstrap";
import { useState } from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";
import { Link } from "react-router-dom";
import "../css/Ingresar.css";

export const Ingresar = () => {
  const history = useHistory();
  const [usuario, setUsuario] = useState("");
  const [password, setPassword] = useState("");
  const [noSePudoLoggear, setNoSePudoLoggear] = useState(false);

  function handleSubmit(event) {
    event.preventDefault();

    let data = {
      usuario: usuario,
      password: password,
    };

    axios
      .post("http://localhost:8080/login", data)
      .then((respuesta) => {
        sessionStorage.setItem("accessToken", respuesta.data);
        sessionStorage.setItem("loggedUsername", usuario);
        history.replace("/home");
      })
      .catch((respuesta) => {
        setNoSePudoLoggear(true);
      });
  }

  return (
    <Card className={"text-center loginCompleto"}>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Alert variant="danger" show={noSePudoLoggear}>
            Tu usuario o contraseña no son correctos{" "}
          </Alert>
          <Form.Group controlId="usuario" className="login-input">
            <Form.Label>Usuario</Form.Label>
            <Form.Control
              type="text"
              value={usuario}
              placeholder="Nombre de usuario"
              onChange={(event) => setUsuario(event.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="password" className="login-input">
            <Form.Label>Contraseña</Form.Label>
            <Form.Control
              type="password"
              value={password}
              placeholder="Contraseña"
              onChange={(event) => setPassword(event.target.value)}
            />
          </Form.Group>
          <div className="botones">
            <div>
              <Button variant="primary" type="submit" className="botonIngresar" disabled={!(password && usuario)}>
                Ingresar
              </Button>
            </div>

            <div className="espacioPregunta">
              ¿No tenes cuenta?
              <Link to="/registrar" class="espaciado">
                Registrate
              </Link>
            </div>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};