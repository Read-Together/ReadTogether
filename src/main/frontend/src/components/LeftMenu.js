import {ListGroup} from "react-bootstrap";
import './LeftMenu.css'

export function LeftMenu() {
  return (
    <ListGroup className="left-menu">
      <ListGroup.Item action href="crear_comunidad">Crear comunidad</ListGroup.Item>
    </ListGroup>  
    )
}