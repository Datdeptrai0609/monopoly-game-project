# GIT FLOW

## Installation

  To make git flow:

```bash
git flow init
```
## FEATURE BRANCH 

creating a feature branch 

```bash
git flow feature start feature_branch 
```
when finish feature branch

```bash
git flow feature finish feature_branch 
```

## HOTFIX 
A hotfix branch can be created using the following methods:

    git flow hotfix start VERSION [BASENAME]

Similar to finishing a release branch, a hotfix branch gets merged into both master and develop: 

    git flow hotfix finish VERSION

## PUBLISH FEATURE 
when do feature together we can publish my feature:

    git flow feature publish MYFEATURE

## PULL FEATURE
To pull anther feature:

    git flow feature publish MYFEATURE

## RELEASE 

 Making release branches 
```bash
git flow release RELEASE [BASE]
``` 
ex: BASE is 0.1.0

Switched to a new branch 'release/RELEASE/0.1.0' 

To finish a release branch, use the following methods: 
```bash
git flow release finish RELEASE
```

## PUBLISH RELEASE

To publish release for teamates:

    git flow release publish RELEASE

## REFERENCES
[GITFLOW](https://danielkummer.github.io/git-flow-cheatsheet/index.vi_VN.html)
