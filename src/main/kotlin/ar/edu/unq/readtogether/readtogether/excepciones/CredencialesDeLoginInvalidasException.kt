package ar.edu.unq.readtogether.readtogether.excepciones

class CredencialesDeLoginInvalidasException : RuntimeException() {

    override val message: String
        get() = "Usuario y/o contraseña inválidos"
}
