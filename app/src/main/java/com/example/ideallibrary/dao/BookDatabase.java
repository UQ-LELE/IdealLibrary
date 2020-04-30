package com.example.ideallibrary.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ideallibrary.R;
import com.example.ideallibrary.entities.Book;
import com.example.ideallibrary.utilities.Fun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "book_database";

    private static BookDatabase instance;

    public abstract BookDao bookDao();

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, "book_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDao bookdao;

        private PopulateDbAsyncTask(BookDatabase db) {
            bookdao = db.bookDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            bookdao.insert(new Book("Les Métamorphoses","","Ovide","Ier siècle",Fun.giveMeYear("Ier siècle"),"Poésie (poème épique)","Rome antique","metamorphose.txt",640, false));
            bookdao.insert(new Book("Énéide","","Virgile","29 – 19 av. J.-C.",Fun.giveMeYear("29 – 19 av. J.-C."),"Poésie (épopée)","Rome antique","eneide.txt",574, false));
            bookdao.insert(new Book("Rāmāyana","","Valmiki","IIe siècle av. J.-C. – IIe siècle",Fun.giveMeYear("IIe siècle av. J.-C. – IIe siècle"),"Poésie (épopée)","Inde","ramayana.txt",249, false));
            bookdao.insert(new Book("Mahâbhârata","","Anonyme","IVe siècle av. J.-C.",Fun.giveMeYear("IVe siècle av. J.-C."),"Poésie (épopée)","Inde","mahabharata.txt",576, false));
            bookdao.insert(new Book("Œdipe roi","","Sophocle","430 av. J.-C.",Fun.giveMeYear("430 av. J.-C."),"Théâtre (tragédie)","Grèce antique","oediperoi.txt",168, false));
            bookdao.insert(new Book("Médée","","Euripide","431 av. J.-C.",Fun.giveMeYear("431 av. J.-C."),"Théâtre (tragédie)","Grèce antique","medee.txt",96, false));
            bookdao.insert(new Book("Les Mille et Une Nuits","","Anonyme","IXe siècle",Fun.giveMeYear("IXe siècle"),"Romanesque (recueil de contes)","Civilisation islamique","milleetunenuits.txt",3504, false));
            bookdao.insert(new Book("Odyssée","","Homère","850–750 av. J.-C.",Fun.giveMeYear("850–750 av. J.-C."),"Poésie (épopée)","Grèce antique","odyssee.txt",608, false));
            bookdao.insert(new Book("Iliade","","Homère","850–750 av. J.-C.",Fun.giveMeYear("850–750 av. J.-C."),"Poésie (épopée)","Grèce antique","iliade.txt",704, false));
            bookdao.insert(new Book("Le Dit du Genji","Murasaki","Shikibu","XIe siècle",Fun.giveMeYear("XIe siècle"),"Romanesque (roman)","Japon","ditdugenji.txt",1536, false));
            bookdao.insert(new Book("Saga de Njáll le Brûlé","","Anonyme","XIIe siècle",Fun.giveMeYear("XIIe siècle"),"Romanesque (saga islandaise)","Islande","sagedenjalllebrule.txt",301, false));
            bookdao.insert(new Book("Masnavî","Djalâl ad-Dîn","Rûmî","XIIe siècle",Fun.giveMeYear("XIIe siècle"),"Poésie (recueil de poèmes mystiques)","Iran","masnavi.txt",112, false));
            bookdao.insert(new Book("Le Jardin des fruits","Dante","Saadi","1257",Fun.giveMeYear("1257"),"Poésie (recueil de poèmes)","Iran","jardindesfruits.txt",112, false));
            bookdao.insert(new Book("Divine Comédie","","Alighieri","1300",Fun.giveMeYear("1300"),"Poésie (épopée)","Italie","divinecomedie.txt",1280, false));
            bookdao.insert(new Book("Décaméron","","Boccace","1349–1353",Fun.giveMeYear("1349–1353"),"Romanesque (recueil de nouvelles)","Italie","decameron.txt",1056, false));
            bookdao.insert(new Book("Gargantua et Pantagruel","François","Rabelais","1532–1534",Fun.giveMeYear("1532–1534"),"Romanesque (romans)","France","gargantuapentagruel.txt",321, false));
            bookdao.insert(new Book("Essais","Michel de ","Montaigne","1595",Fun.giveMeYear("1595"),"Argumentation (essai philosophique)","France","essais.txt",1376, false));
            bookdao.insert(new Book("Hamlet","William","Shakespeare","1603",Fun.giveMeYear("1603"),"Théâtre (tragédie)","Royaume-Uni","hamlet.txt",127, false));
            bookdao.insert(new Book("Don Quichotte","Miguel de ","Cervantes","1605–1615",Fun.giveMeYear("1605–1615"),"Romanesque (roman)","Espagne","donquichotte.txt",1200, false));
            bookdao.insert(new Book("Le Roi Lear","William ","Shakespeare","1608",Fun.giveMeYear("1608"),"Théâtre (tragédie)","Royaume-Uni","leroilear.txt",435, false));
            bookdao.insert(new Book("Othello ou le Maure de Venise","William ","Shakespeare","1622",Fun.giveMeYear("1622"),"Théâtre (tragédie)","Royaume-Uni","othello.txt",96, false));
            bookdao.insert(new Book("Les Voyages de Gulliver","Jonathan ","Swift","1726",Fun.giveMeYear("1726"),"Romanesque (roman)","Irlande","voyagedegulliver.txt",430, false));
            bookdao.insert(new Book("Vie et opinions de Tristram Shandy gentilhomme","Laurence","Sterne","1760",Fun.giveMeYear("1760"),"Romanesque (roman)","Irlande","vieetopiniondetristramshandy.txt",960, false));
            bookdao.insert(new Book("Jacques le Fataliste et son maître","Denis","Diderot","1796",Fun.giveMeYear("1796"),"Romanesque (roman)","France","jacqueslefataliste.txt",415, false));
            bookdao.insert(new Book("Faust","Johan Wolfgang von ","Goethe","1808",Fun.giveMeYear("1808"),"Théâtre (tragédie)","Allemagne","faust.txt",320, false));
            bookdao.insert(new Book("Orgueil et Préjugés","Jane ","Austen","1813",Fun.giveMeYear("1813"),"Romanesque (roman)","Royaume-Uni","orgueiletprejuges.txt",512, false));
            bookdao.insert(new Book("Les poèmes de...","Giacomo ","Leopardi","1818",Fun.giveMeYear("1818"),"Poésie (œuvre poétique)","Italie","poemesdegiacomo.txt",112, false));
            bookdao.insert(new Book("Le Rouge et le Noir","","Stendhal","1830",Fun.giveMeYear("1830"),"Romanesque (roman)","France","rougeetnoir.txt",576, false));
            bookdao.insert(new Book("Le Père Goriot","Honoré de ","Balzac","1835",Fun.giveMeYear("1835"),"Romanesque (roman)","France","peregoriot.txt",436, false));
            bookdao.insert(new Book("Contes","Hans Christian ","Andersen","1835–1837",Fun.giveMeYear("1835–1837"),"Romanesque (recueil de contes)","Danemark","contes.txt",480, false));
            bookdao.insert(new Book("Les Contes d'...","Edgar Allan","Poe","1840",Fun.giveMeYear("1840"),"Romanesque (nouvelles et contes)","États-Unis","contesdepoe.txt",0, false));
            bookdao.insert(new Book("Les Âmes mortes","Nicolas","Gogol","1842",Fun.giveMeYear("1842"),"Romanesque (roman)","Russie (Empire russe)","amesmortes.txt",512, false));
            bookdao.insert(new Book("Les Hauts de Hurlevent","Emily ","Brontë","1847",Fun.giveMeYear("1847"),"Romanesque (roman)","Royaume-Uni","hautsdehurlevent.txt",488, false));
            bookdao.insert(new Book("Moby Dick","Herman ","Melville","1851",Fun.giveMeYear("1851"),"Romanesque (roman)","États-Unis","mobydick.txt",752, false));
            bookdao.insert(new Book("Feuilles d'herbe","Walt ","Whitman","1855",Fun.giveMeYear("1855"),"Poésie (recueil de poèmes)","États-Unis","feuillesdherbe.txt",190, false));
            bookdao.insert(new Book("Madame Bovary","Gustave ","Flaubert","1857",Fun.giveMeYear("1857"),"Romanesque (roman)","France","madamebovary.txt",576, false));
            bookdao.insert(new Book("Les Grandes Espérances","Charles ","Dickens","1861",Fun.giveMeYear("1861"),"Romanesque (roman)","Royaume-Uni","grandesesperances.txt",752, false));
            bookdao.insert(new Book("Guerre et Paix","Léon ","Tolstoï","1865–1869",Fun.giveMeYear("1865–1869"),"Romanesque (roman)","Russie (Empire russe)","guerreetpaix.txt",2070, false));
            bookdao.insert(new Book("Crime et Châtiment","Fiodor ","Dostoïevski","1866",Fun.giveMeYear("1866"),"Romanesque (roman)","Russie (Empire russe)","crimeetchatiment.txt",704, false));
            bookdao.insert(new Book("L'Idiot","Fiodor ","Dostoïevski","1869",Fun.giveMeYear("1869"),"Romanesque (roman)","Russie (Empire russe)","lidiot.txt",960, false));
            bookdao.insert(new Book("L'Éducation sentimentale","Gustave ","Flaubert","1869",Fun.giveMeYear("1869"),"Romanesque (roman)","France","educationsentimentale.txt",668, false));
            bookdao.insert(new Book("Middlemarch","George","Eliot","1871",Fun.giveMeYear("1871"),"Romanesque (roman)","Royaume-Uni","middlemarch.txt",1152, false));
            bookdao.insert(new Book("Les Démons","Fiodor ","Dostoïevski","1872",Fun.giveMeYear("1872"),"Romanesque (roman)","Russie (Empire russe)","demons.txt",896, false));
            bookdao.insert(new Book("Anna Karénine","Léon ","Tolstoï","1877",Fun.giveMeYear("1877"),"Romanesque (roman)","Russie (Empire russe)","annakarenine.txt",1024, false));
            bookdao.insert(new Book("Une maison de poupée","Henrik ","Ibsen","1879",Fun.giveMeYear("1879"),"Théâtre (pièce de théâtre)","Norvège","maisondepoupee.txt",160, false));
            bookdao.insert(new Book("Les Frères Karamazov","Fiodor ","Dostoïevski","1880",Fun.giveMeYear("1880"),"Romanesque (roman)","Russie (Empire russe)","frereskaramazov.txt",920, false));
            bookdao.insert(new Book("Les Aventures de Huckleberry Finn","Mark ","Twain","1884",Fun.giveMeYear("1884"),"Romanesque (roman picaresque)","États-Unis","aventuredehuckeberryfinn.txt",352, false));
            bookdao.insert(new Book("Récits divers","Anton ","Tchekhov","1886",Fun.giveMeYear("1886"),"Romanesque (récits)","Russie (Empire russe)","recitsdeivestchekov.txt",0, false));
            bookdao.insert(new Book("La Mort d'Ivan Ilitch","Léon ","Tolstoï","1886",Fun.giveMeYear("1886"),"Romanesque (roman)","Russie (Empire russe)","mortdivanilitch.txt",288, false));
            bookdao.insert(new Book("La Faim","Kanut ","Hamsun","1890",Fun.giveMeYear("1890"),"Romanesque (roman)","Norvège","faim.txt",288, false));
            bookdao.insert(new Book("Épopée de Gilgamesh","","Anonyme","XVIIIe – XVIIe siècle av. J.-C.",Fun.giveMeYear("XVIIIe – XVIIe siècle av. J.-C."),"Poésie (épopée)","Mésopotamie","epopeedegilgamesh.txt",190, false));
            bookdao.insert(new Book("Les Buddenbrook","Thomas ","Mann","1901",Fun.giveMeYear("1901"),"Romanesque (roman)","Allemagne","buddenbrook.txt",864, false));
            bookdao.insert(new Book("Nostromo","Joseph ","Conrad","1904",Fun.giveMeYear("1904"),"Romanesque (roman)","Royaume-Uni","nostromo.txt",560, false));
            bookdao.insert(new Book("Amants et Fils","David Herbert ","Lawrence","1913",Fun.giveMeYear("1913"),"Romanesque (roman)","Royaume-Uni","amantsetfils.txt",640, false));
            bookdao.insert(new Book("À la recherche du temps perdu","Marcel ","Proust","1913–1927",Fun.giveMeYear("1913–1927"),"Romanesque (roman)","France","recherchedutemps.txt",3000, false));
            bookdao.insert(new Book("Le Journal d'un fou","Lu ","Xun","1918",Fun.giveMeYear("1918"),"Romanesque (nouvelle)","Chine","journaldunfou.txt",0, false));
            bookdao.insert(new Book("Ulysse","James ","Joyce","1922",Fun.giveMeYear("1922"),"Romanesque (roman)","Irlande","ulysse.txt",1184, false));
            bookdao.insert(new Book("La Conscience de Zeno","Italo ","Svevo","1923",Fun.giveMeYear("1923"),"Romanesque (roman)","Italie","consciencedezeno.txt",576, false));
            bookdao.insert(new Book("La Montagne magique","Thomas ","Mann","1924",Fun.giveMeYear("1924"),"Romanesque (roman)","Allemagne","montagnemagique.txt",1176, false));
            bookdao.insert(new Book("Les nouvelles de...","Franz","Kafka","1924",Fun.giveMeYear("1924"),"Romanesque (nouvelles)","Tchécoslovaquie","nouvellesdekafka.txt",0, false));
            bookdao.insert(new Book("Le Procès","Franz","Kafka","1925",Fun.giveMeYear("1925"),"Romanesque (roman)","Tchécoslovaquie","proces.txt",285, false));
            bookdao.insert(new Book("Mrs Dalloway","Virginia","Woolf","1925",Fun.giveMeYear("1925"),"Romanesque (roman)","Royaume-Uni","mrsdalloway.txt",368, false));
            bookdao.insert(new Book("Le Château","Franz","Kafka","1926",Fun.giveMeYear("1926"),"Romanesque (roman)","Tchécoslovaquie","chateau.txt",544, false));
            bookdao.insert(new Book("La Promenade au phare","Virginia","Woolf","1927",Fun.giveMeYear("1927"),"Romanesque (roman)","Royaume-Uni","promenadeauphare.txt",277, false));
            bookdao.insert(new Book("Romancero gitano","Federico ","García Lorca","1928",Fun.giveMeYear("1928"),"Poésie (recueil de poèmes)","Espagne","romancerogitano.txt",136, false));
            bookdao.insert(new Book("Berlin Alexanderplatz","Alfred","Döblin","1929",Fun.giveMeYear("1929"),"Romanesque (roman)","Allemagne","berlinalexanderplatz.txt",640, false));
            bookdao.insert(new Book("Le Bruit et la Fureur","William","Faulkner","1929",Fun.giveMeYear("1929"),"Romanesque (roman)","États-Unis","bruitetfureur.txt",384, false));
            bookdao.insert(new Book("L'Homme sans qualités","Robert","Musil","1930–1932",Fun.giveMeYear("1930–1932"),"Romanesque (roman inachevé)","Autriche","hommesansqualites.txt",1312, false));
            bookdao.insert(new Book("Voyage au bout de la nuit","Louis-Ferdinand","Céline","1932",Fun.giveMeYear("1932"),"Romanesque (roman)","France","voyageauboutdelanuit.txt",512, false));
            bookdao.insert(new Book("Gens indépendants","Halldór ","Laxness","1934–1935",Fun.giveMeYear("1934–1935"),"Romanesque (roman)","Islande","gensindépendants.txt",498, false));
            bookdao.insert(new Book("Absalon Absalon !","William","Faulkner","1936",Fun.giveMeYear("1936"),"Romanesque (roman)","États-Unis","absalonabsalon.txt",432, false));
            bookdao.insert(new Book("L'Étranger","Albert","Camus","1942",Fun.giveMeYear("1942"),"Romanesque (roman)","France","etranger.txt",192, false));
            bookdao.insert(new Book("Fictions","Jorge Luis ","Borges","1944",Fun.giveMeYear("1944"),"Romanesque (recueil de nouvelles)","Argentine","fictions.txt",216, false));
            bookdao.insert(new Book("Fifi Brindacier","Astrid","Lindgren","1945",Fun.giveMeYear("1945"),"Romanesque (roman)","Suède","fifibrindacier.txt",160, false));
            bookdao.insert(new Book("Alexis Zorba","Nikos ","Kazantzákis","1946",Fun.giveMeYear("1946"),"Romanesque (roman)","Grèce","alexiszorba.txt",348, false));
            bookdao.insert(new Book("1984","George","Orwell","1949",Fun.giveMeYear("1949"),"Romanesque (roman)","Royaume-Uni","1984.txt",400, false));
            bookdao.insert(new Book("Mémoires d'Hadrien","Marguerite","Yourcenar","1951",Fun.giveMeYear("1951"),"Romanesque (roman)","France","memoiresdhadrien.txt",364, false));
            bookdao.insert(new Book("Trilogie : Molloy - Malone meurt - L'Innommable","Samuel ","Beckett","1951–1953",Fun.giveMeYear("1951–1953"),"Romanesque (trilogie romanesque)","Irlande","trilogiemolloy.txt",0, false));
            bookdao.insert(new Book("Les poèmes de...","Paul ","Celan","1952",Fun.giveMeYear("1952"),"Poésie (œuvre poétique)","France","poemesdecelan.txt",162, false));
            bookdao.insert(new Book("Homme invisible pourquoi chantes-tu ?","Ralph ","Ellison","1952",Fun.giveMeYear("1952"),"Romanesque (roman)","États-Unis","hommeinvisible.txt",0, false));
            bookdao.insert(new Book("Le Vieil Homme et la Mer","Ernest","Hemingway","1952",Fun.giveMeYear("1952"),"Romanesque (roman)","États-Unis","vielhommeetlamer.txt",574, false));
            bookdao.insert(new Book("Le Grondement de la montagne","Yasunari ","Kawabata","1954",Fun.giveMeYear("1954"),"Romanesque (roman)","Japon","grondementdelamontagne.txt",272, false));
            bookdao.insert(new Book("Lolita","Vladimir ","Nabokov","1955",Fun.giveMeYear("1955"),"Romanesque (roman)","Russie","lolita.txt",192, false));
            bookdao.insert(new Book("Pedro Páramo","Juan ","Rulfo","1955",Fun.giveMeYear("1955"),"Romanesque (roman)","Mexique","pedroparamo.txt",544, false));
            bookdao.insert(new Book("Diadorim (Grande Sertão: veredas)","Joâo ","Guimarães Rosa","1956",Fun.giveMeYear("1956"),"Romanesque (roman)","Brésil","diadorim.txt",508, false));
            bookdao.insert(new Book("Le monde s'effondre","Chinua ","Achebe","1958",Fun.giveMeYear("1958"),"Romanesque (roman)","Nigeria","lemondeseffondre.txt",243, false));
            bookdao.insert(new Book("Le Tambour","Günter","Grass","1959",Fun.giveMeYear("1959"),"Romanesque (roman)","Allemagne","tambour.txt",648, false));
            bookdao.insert(new Book("Les Fils de la Médina","Naguib","Mahfouz","1959",Fun.giveMeYear("1959"),"Romanesque (roman)","Égypte","filsdelamedina.txt",528, false));
            bookdao.insert(new Book("Le Carnet d'or","Doris ","Lessing","1962",Fun.giveMeYear("1962"),"Romanesque (roman)","Royaume-Uni","carnetdor.txt",960, false));
            bookdao.insert(new Book("Cent ans de solitude","Gabriel ","García Márquez","1967",Fun.giveMeYear("1967"),"Romanesque (roman)","Colombie","centansdesolitude.txt",480, false));
            bookdao.insert(new Book("Saison de la migration vers le nord","Tayeb ","Salih","1971",Fun.giveMeYear("1971"),"Romanesque (roman)","Soudan","saisondelamigrationverslenord.txt",176, false));
            bookdao.insert(new Book("La storia","Elsa","Morante","1974",Fun.giveMeYear("1974"),"Romanesque (roman)","Italie","lastoria.txt",960, false));
            bookdao.insert(new Book("Les Enfants de minuit","Salman ","Rushdie","1981",Fun.giveMeYear("1981"),"Romanesque (roman)","Inde","enfantsdeminuit.txt",816, false));
            bookdao.insert(new Book("Le Livre de l'intranquillité","Fernando","Pessoa","1982",Fun.giveMeYear("1982"),"Poésie (recueil de poèmes en prose de pensées et d'aphorismes …)","Portugal","livredelintranquilite.txt",624, false));
            bookdao.insert(new Book("L'Amour aux temps du choléra","Gabriel ","García Márquez","1985",Fun.giveMeYear("1985"),"Romanesque (roman)","Colombie","amourauxtempsducholera.txt",448, false));
            bookdao.insert(new Book("Beloved","Toni ","Morrison","1987",Fun.giveMeYear("1987"),"Romanesque (roman)","États-Unis","beloved.txt",384, false));
            bookdao.insert(new Book("L'Aveuglement","José","Saramago","1995",Fun.giveMeYear("1995"),"Romanesque (roman)","Portugal","aveuglement.txt",384, false));
            bookdao.insert(new Book("La Reconnaissance de Shâkountalâ","","Kâlidâsa","IIe siècle av. J.-C. – IVe siècle",Fun.giveMeYear("IIe siècle av. J.-C. – IVe siècle"),"Théâtre (drame)","Inde","reconnaissancedeshakountala.txt",218, false));
            bookdao.insert(new Book("Livre de Job","","Anonyme","VIe – IVe siècle av. J.C",Fun.giveMeYear("VIe – IVe siècle av. J.C"),"Écrit religieux (poème en prose)","Empire achéménide","livredejob.txt",101, false));
            bookdao.insert(new Book("Les Contes de Canterbury","Geoffrey ","Chaucer","XIVe siècle",Fun.giveMeYear("XIVe siècle"),"Romanesque (recueil de nouvelles)","Royaume-Uni","contesdecanterbury.txt",832, false));
            return null;
        }
    }

}
