FEATURE BRANCH 
    creating a feature branch 
            git flow feature start feature_branch 
    when finish feature branch
            git flow feature finish feature_branch
RELEASE 
    Making release branches 
            git flow release start 0.1.0 
        Switched to a new branch 'release/0.1.0' 
    To finish a release branch, use the following methods: 
            git flow release finish '0.1.0'

HOTFIX 
    A hotfix branch can be created using the following methods: 
            git flow hotfix start hotfix_branch 
        Similar to finishing a release branch, a hotfix branch gets merged into both master and develop 
            git flow hotfix finish hotfix_branch
