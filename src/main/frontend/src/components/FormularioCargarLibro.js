import { useState } from "react";
import NavBar from "./NavBar";
import { Alert, Button, Container, Form } from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router";
import "../css/FormularioCargarLibro.css";

const FormularioCargarLibro = () => {
  const { id } = useParams();
  const [fueCreado, setFueCreado] = useState(false);
  const [nombre, setNombre] = useState("");
  const [autor, setAutor] = useState("");
  const [link, setLink] = useState("");
  const history = useHistory();

  const handleSubmit = (event) => {
    const headers = {
      authorization: sessionStorage.getItem("accessToken"),
    };
    const data = { nombre: nombre, autor: autor, link: link };

    event.preventDefault();

    axios
      .post(`http://localhost:8080/grupos/${id}/biblioteca`, data, {
        headers: headers,
      })
      .then(() => {
        setFueCreado(true);
        history.push(`/grupos/${id}/biblioteca`);
      });
  };

  return (
    <div>
      <NavBar />
      <Alert variant="success" show={fueCreado}>
        ¡Genial! Has cargado el libro {nombre}{" "}
      </Alert>
      <Container className="card cardCargarLibro formularioCargarLibro">
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="nombre">
            <Form.Label>Nombre del libro</Form.Label>
            <Form.Control
              type="text"
              placeholder="Nombre"
              value={nombre}
              onChange={(event) => setNombre(event.target.value)}
            />
          </Form.Group>

          <Form.Group>
            <Form.Label>Autor</Form.Label>
            <Form.Control
              type="text"
              rows={3}
              placeholder="Autor"
              value={autor}
              onChange={(event) => setAutor(event.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="link">
            <Form.Label>Link del libro</Form.Label>
            <Form.Control
              type="text"
              placeholder="Link"
              value={link}
              onChange={(event) => setLink(event.target.value)}
            />
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            className="botonCargar"
            disabled={!(nombre && link && autor)}
          >
            Crear
          </Button>
          <Link to={`/grupos/${id}/biblioteca`}>
            <Button className="btn btn-danger botonVolver">Volver</Button>
          </Link>
        </Form>
      </Container>
    </div>
  );
};

export default FormularioCargarLibro;
