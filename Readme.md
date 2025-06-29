# Skypay Technical Test 1 – Banking Service


## Description

Ce projet implémente les fonctionnalités de base d'un système bancaire incluant le dépôt d'argent, le retrait d'argent et l'affichage des transactions. Le système respecte les exigences typiques d'un logiciel bancaire en termes de sécurité, performance et gestion des erreurs.

## Fonctionnalités

- **Dépôt d'argent** : Permet d'ajouter des fonds sur un compte
- **Retrait d'argent** : Permet de retirer des fonds d'un compte (avec vérification du solde)
- **Affichage des transactions** : Génère un relevé bancaire avec l'historique complet des opérations

## Interface Publique

La classe `AccountManager` implémente l'interface publique suivante :

```java
public interface AccountService {
    void deposit(int mount);
    void withdraw(int mount);
    void printStatement();
}
```

## Comportement Attendu

### Scénario d'Acceptance Test

**Étant donné** qu'un client effectue :
- Un dépôt de 1000 le 10-01-2012
- Un dépôt de 2000 le 13-01-2012
- Un retrait de 500 le 14-01-2012

**Quand** il demande l'impression de son relevé bancaire

**Alors** il devrait voir un relevé formaté avec toutes les transactions et les soldes mis à jour

## Exigences Techniques

### Gestion des Erreurs
- Validation des montants (pas de montants négatifs)
- Vérification du solde suffisant pour les retraits
- Validation du format de date
- Gestion des entrées nulles ou invalides

### Contraintes
- **Pas de repositories** : Utilisation exclusive d'`ArrayList`

## Structure du Projet

```
src/
├── main/
│   └── java/
│       └── com.skypay/
│           ├── entity/
│           │   └── Transaction.java
│           ├── manager/
│           │   └── AccountManager.java
│           └── service/
│               ├── AccountService.java
│               ├── Account.java
│               └── Main.java
└── test/
    └── java/
        └── com.skypay.manager/
            └── AccountTest.java

```


## Exemple d'Utilisation

```java
public class Main {
    public static void main(String[] args) {

        Account account = new Account(new AccountManager());

        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);
        account.printStatement();
    }
}
```

## Format du Relevé

Le relevé bancaire affiche les informations suivantes :
- Date de la transaction
- Type d'opération (Dépôt/Retrait)
- Montant de la transaction
- Solde après la transaction

Exemple de sortie :
```
Date       | Amount | Balance
14-01-2012 | -500   | 2500
13-01-2012 | 2000   | 3000
10-01-2012 | 1000   | 1000
```

## Gestion des Erreurs

Le système gère les cas d'erreur suivants :
- **Montant invalide** : Exception levée si le montant est négatif ou zéro
- **Solde insuffisant** : Exception levée si le retrait dépasse le solde disponible
- **Limite atteinte** : Exception levée si le montant depasse la limite de retrait

## Tests

Le projet inclut une suite de tests complète couvrant :
- Tests unitaires pour chaque méthode publique
- Tests d'intégration pour les scénarios complets
- Tests de gestion d'erreurs
