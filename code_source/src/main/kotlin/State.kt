class State(
    private val nom: String,
    private val delta: HashMap<Char, State?>
) {
    /**
     * Récupère l'état suivant en fonction du caractère donné en paramètre.
     * @param char le caractère pour lequel récupérer l'état suivant.
     * @return l'état suivant correspondant au caractère donné, ou null si la transition n'existe pas.
     */
    fun getDelta(char: Char) : State?{
        return this.delta[char]
    }

    /**
     * Récupère l'ensemble des transitions de l'état.
     * @return un hashmap contenant les transitions de l'état.
     */
    fun getDelta() : HashMap<Char, State?>{
        return delta
    }

    /**
     * Ajoute une transition à l'état.
     * @param char le caractère de la transition.
     * @param state l'état suivant de la transition.
     */
    fun addToDelta(char: Char, state: State) {
        delta[char] = state
    }

    /**
     * Ajoute plusieurs transitions à l'état en une seule fois.
     * @param hm un hashmap contenant les caractères et les états suivants des transitions à ajouter.
     */
    fun addToDelta(hm : HashMap<Char, State>) {
        for (i in hm){
            delta[i.key] = i.value
        }
    }

    override fun toString(): String {
        return this.nom
    }
}