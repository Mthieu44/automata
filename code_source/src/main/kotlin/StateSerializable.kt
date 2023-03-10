
@kotlinx.serialization.Serializable
class StateSerializable(
    private val nom: String,
    private val delta: HashMap<Char, String>
)  {
    /**
     * Récupère le nom de l'état.
     *
     * @return Le nom de l'état.
     */
    fun getNom(): String {
        return nom
    }

    /**
     * Récupère le delta de l'état.
     *
     * @return Le delta de l'état.
     */
    fun getDelta(): HashMap<Char, String> {
        return delta
    }
}