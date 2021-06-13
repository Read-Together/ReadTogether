import { Alert, Button, Container, Form } from "react-bootstrap";
import { useState } from "react";
import axios from "axios";
import "../css/FormularioCrearComunidad.css";
import NavBar from "./NavBar";
import { Link, useHistory} from "react-router-dom";

export function FormularioCrearComunidad() {
  const [nombre, setNombre] = useState("");
  const [descripcion, setdescripcion] = useState("");
  const [fueCreado, setFueCreado] = useState(false);
  const [noFueCreado, setNoFueCreado] = useState(false);
  const history = useHistory()

  const handleSubmit = (event) => {
    const headers = {
      authorization: sessionStorage.getItem("accessToken"),
    };
    const data = { userName: sessionStorage.getItem("loggedUsername") };

    event.preventDefault();

    axios
      .post(
        "http://localhost:8080/grupos",
        {
          nombre: nombre,
          descripcion: descripcion,
        },
        { headers: headers }
      )
      .then((response) => {
        console.log(response.data);
        setFueCreado(true);
        axios.post(`http://localhost:8080/grupos/${response.data}/registrar`, data, {
          headers: headers
        })
        .then(() => {
          history.push(`/grupos/${response.data}`);
        })
      })
      .catch(() => {
        setNoFueCreado(true);
      });
  };

  return (
    <div>
      <NavBar />

      <Container className="formularioComunidad">
        <Form onSubmit={handleSubmit}>
          <Alert variant="success" show={fueCreado}>
            ¡Genial! Has creado el grupo {nombre}{" "}
          </Alert>
          <Alert variant="danger" show={noFueCreado}>
            ¡Ups! Ocurrió un error creando el grupo {nombre}, intenta mas tarde{" "}
          </Alert>
          <Form.Group controlId="nombre">
            <Form.Label>Nombre de la comunidad</Form.Label>
            <Form.Control
              type="text"
              placeholder="Nombre"
              value={nombre}
              onChange={(event) => setNombre(event.target.value)}
            />
          </Form.Group>

          <Form.Group>
            <Form.Label>Descripción</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              placeholder="Cuenta a la gente de que se trata"
              value={descripcion}
              onChange={(event) => setdescripcion(event.target.value)}
            />
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            className="botonCrear"
            disabled={!(nombre && descripcion)}
          >
            Crear
          </Button>
          <Link to="/home">
            <Button className="btn btn-danger botonVolverForm">Volver</Button>
          </Link>
        </Form>
      </Container>
    </div>
  );
}
