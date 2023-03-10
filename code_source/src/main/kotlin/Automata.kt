import java.io.File

class Automata(
    private val nom: String,
    private val stateList: List<State>,
    private val alphabet: List<Char>,
    private val startState: State,
    private val endState: List<State>,
) {
    /**
     * Vérifie si le mot donné en entrée est reconnu par l'automate.
     *
     * @param mot Le mot à tester.
     *
     * @return Un booléen qui indique si le mot est reconnu par l'automate ou non.
     */
    fun analyse(mot: String) : Boolean{
        //On commence à l'état initial
        var currentState: State? = startState
        //Pour chacun des caractères dans le mot
        for (c in mot){
            //On récupère l'état que l'on atteint à partir de l'état actuel et du caractère
            currentState = currentState?.getDelta(c)
            //Si on ne récupère rien, cela veut dire que l'état n'a pas la transition avec le caractère, donc le mot
            //n'est pas reconnu
            if (currentState == null)
                return false
        }
        //Après avoir parcouru toutes les lettres, on vérifie que l'on est bien arrivé sur l'un des états finaux
        return (currentState in endState)
    }

    /**
     * Exporte l'automate dans un fichier .dot dans le répertoire "./exports/".
     * Le fichier porte le nom de l'automate et contient les informations nécessaires pour la visualisation de l'automate.
     * Si le répertoire n'existe pas, il sera créé.
     */
    fun export() {
        //Création du répertoire si il n'existe pas
        val dir = File("./exports/")
        if (!dir.isDirectory)
            dir.mkdir()

        //Remplacement du fichier
        val file = File("./exports/$nom.dot")
        file.delete()
        file.createNewFile()

        //Création du graph orienté et forme de cercle pour les noeuds
        var content = "digraph $nom {\nnode [shape=circle]\n"

        //Les états finis ont une forme de cercle double
        for (s in endState){
            content += "$s [shape=doublecircle]\n"
        }
        //Création d'un noeud invisible pour faire la flèche d'entrée dans le graph
        content+= "invis [style=invis,label=a]\ninvis -> $startState \n"

        //On parcourt tous les états, et toutes leurs transitions, que l'on ajoute au contenu
        for (s in stateList){
            for (d in s.getDelta()){
                content+= "$s -> ${d.value} [label=\"${d.key}\"];\n"
            }
        }
        content+= "}"
        //On écrit le texte dans le fichier
        file.writeText(content)

        println("Le fichier se trouve à l'emplacement ./exports/$nom.dot")
    }

    override fun toString(): String {
        return nom
    }
}