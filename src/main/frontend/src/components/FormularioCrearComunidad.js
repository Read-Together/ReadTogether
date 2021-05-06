import {Button, Form} from "react-bootstrap";

export function FormularioCrearComunidad() {
  return (
    <Form>
      <Form.Group controlId="nombre">
        <Form.Label>Nombre de la comunidad</Form.Label>
        <Form.Control type="text" placeholder="Nombre"/>
      </Form.Group>

      <Form.Group>
        <Form.Label>Descripción</Form.Label>
        <Form.Control as="textarea" rows={3} placeholder="Cuenta de que se trata"/>
      </Form.Group>

      <Button variant="primary" type="submit">
        Crear
      </Button>
    </Form>
  )

}