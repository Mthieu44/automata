import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import kotlin.system.exitProcess

fun main() {
    //Création de la liste d'automates en lisant tous les fichiers dans le dossier 'automates'
    val autoList = mutableListOf<Automata>()
    File("./automates/").listFiles()?.forEach {
        autoList.add(readFromFile(it))
    }

    println("------------------------")
    println("Entrez QUIT à tout moment pour quitter")

    //Boucle infinie pour l'exécution du programme
    //L'utilisateur commence par choisir un premier automate
    var a = chooseAutomate(autoList)
    while (true){
        //L'utilisateur choisit si il veut utiliser l'automate ou l'exporter
        if (choose() == 1){
            testWord(a)
        }else{
            a.export()
        }
        //L'utilisateur choisit si il veut changer d'automate ou garder le même
        if (restart() == 2)
            a = chooseAutomate(autoList)
    }
}

/**
 * Lit un automate à partir d'un fichier.
 *
 * @param file Un objet `File` représentant le fichier à lire, en format json.
 *
 * @return Un objet `Automata` qui représente l'automate désérialisé à partir du fichier.
 *
 */
fun readFromFile(file: File) : Automata{
    //Permet d'ignorer les clés inconnues dans le json
    val json = Json { ignoreUnknownKeys = true }
    //On déserialise le fichier dans un objet AutomataSerializable, puis on le convertit en Automata
    return json.decodeFromStream<AutomataSerializable>(file.inputStream()).decode()
}


/**
 * Permet de choisir un automate parmi une liste d'automates fournie.
 *
 * @param autoList Une liste mutable d'objets `Automata` parmi lesquels l'utilisateur doit choisir.
 *
 * @return Un objet `Automata` choisi par l'utilisateur.
 *
 */
fun chooseAutomate(autoList: MutableList<Automata>) : Automata {
    //Affiche la liste des automates dans la liste fournie
    println("------------------------")
    println("Choisissez un automate :")
    for (i in autoList.indices){
        println("$i - ${autoList[i]}")
    }
    println("Plus à venir...")

    //Lit le choix de l'utilisateur
    val ct = readln()
    //Quitte le programme
    if (ct == "QUIT")
        exitProcess(0)

    //On vérifie que l'entrée est un entier et qu'elle correspond à un automate
    return try {
        val c = ct.toInt()
        autoList[c]
    }catch (e : java.lang.NumberFormatException){
        println("Paramètre invalide")
        chooseAutomate(autoList)
    }catch (e : IndexOutOfBoundsException){
        println("Paramètre invalide")
        chooseAutomate(autoList)
    }
}

/**
 * Permet à l'utilisateur de choisir entre deux actions.
 *
 * @return Un entier correspondant au choix de l'utilisateur. 1 pour utiliser l'automate, 2 pour exporter l'automate.
 */
fun choose() : Int {
    println("------------------------")
    println("Que souhaitez-vous faire ?")
    println("1 - Utiliser l'automate        2 - Exporter l'automate")
    when (readln()) {
        "1" -> return 1
        "2" -> return 2
        "QUIT" -> exitProcess(0)
    }
    println("Paramètre invalide")
    return choose()
}

/**
 * Permet à l'utilisateur de tester un mot avec un automate donné.
 *
 * @param a L'automate à utiliser pour tester le mot.
 */
fun testWord(a : Automata){
    println("------------------------")
    println("Entrez le mot à tester :")
    val mot = readln()
    println("------------------------")
    if (mot == "QUIT")
        exitProcess(0)
    //On appelle la méthode 'analyse' de l'automate
    if (a.analyse(mot)){
        println("Le mot est reconnu")
    }else{
        println("Le mot n'est pas reconnu")
    }
}

/**
 * Permet à l'utilisateur de choisir entre garder le même automate ou en choisir un nouveau.
 *
 * @return Un entier correspondant au choix de l'utilisateur : 1 pour garder le même automate, 2 pour en choisir un nouveau.
 */
fun restart() : Int{
    println("------------------------")
    println("Souhaitez vous garder le même automate ou changer ?")
    println("1 - Le même        2 - Changer")
    when (readln()) {
        "1" -> return 1
        "2" -> return 2
        "QUIT" -> exitProcess(0)
    }
    println("Paramètre invalide")
    return restart()
}

