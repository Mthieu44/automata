import java.io.File
import java.util.ArrayList

@Suppress("DEPRECATION")
@kotlinx.serialization.Serializable
class AutomataSerializable(
    private val nom: String,
    private val stateList: Array<String>,
    private val alphabet: Array<Char>,
    private val startState: String,
    private val endState: Array<String>,
    private val delta: Array<StateSerializable>
) {
    /**
     * Décode le contenu JSON en un automate fini déterministe. La méthode renvoie l'automate ainsi créé.
     *
     * @return L'automate fini déterministe créé à partir du contenu JSON.
     */
    fun decode() : Automata {
        // Crée une liste de State à partir des noms de states dans `stateList`
        val sl = stateList.map { State(it, hashMapOf()) }

        // On parcourt chaque State pour y ajouter les transitions sortantes
        for (s in sl){
            //On trouve les transition associée au State
            val d = delta.find {(it.getNom() == s.toString())} ?.getDelta()
            if (d != null) {
                //On ajoute chacune des transitions dans le State
                for (i in d){
                    sl.find { (it.toString() == i.value) }?.let { s.addToDelta(i.key, it) }
                }
            }
        }

        //On cherche l'état initial
        val ss = sl.find {(startState == it.toString())}

        //On cherche les états finaux
        val es = mutableListOf<State>()
        for (i in endState){
            sl.find { i == it.toString() }?.let { es.add(it) }
        }

        //On crée l'automate
        return Automata(nom, sl, alphabet.toList(), ss!!, es)
    }
}