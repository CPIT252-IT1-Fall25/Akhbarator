package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

interface OrderingStrategy {
    Article[] order(Article[] articles);

}


