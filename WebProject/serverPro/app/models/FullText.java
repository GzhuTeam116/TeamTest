/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import play.db.DB;

/**
 *
 * @author User
 */
public class FullText {
    private static final String indexDir = "LuIndex";
    public class Writer {
        SqlConnect sql;
        IndexWriter writer;
        public Writer() throws IOException {
            Directory dir = FSDirectory.open(new File(indexDir));
            IndexWriterConfig config = new IndexWriterConfig(
                    Version.LUCENE_4_9,
                    new SmartChineseAnalyzer(Version.LUCENE_4_9)
            );
            writer = new IndexWriter(dir, config);
            sql = new SqlConnect(DB.getConnection());
        }
        private Document GetDocument(int tid) throws SQLException {
            String sqlStr = "Select t_resource.tid id, name, url, price, author, speciesName, press, introduction\n";
            sqlStr += "From t_resource, t_species Where t_resource.tid = "+tid+" and t_species.tid = location";
            ResultSet ans = sql.Query(sqlStr); ans.next();
            Document doc = new Document();
            doc.add(new Field("id",ans.getString("id"),Field.Store.YES,Field.Index.NOT_ANALYZED));
            doc.add(new Field("name",ans.getString("name"),Field.Store.YES,Field.Index.ANALYZED));
            doc.add(new Field("icon",ans.getString("url"),Field.Store.YES,Field.Index.NO));
            doc.add(new Field("price",ans.getString("price"),Field.Store.YES,Field.Index.NO));
            String[] authors = ans.getString("author").split(" ");
            for (String author : authors) {
                doc.add(new Field("author",author,Field.Store.NO,Field.Index.NOT_ANALYZED));
            }
            doc.add(new Field("species",ans.getString("speciesName"),Field.Store.NO,Field.Index.ANALYZED));
            doc.add(new Field("press",ans.getString("press"),Field.Store.NO,Field.Index.ANALYZED));
            doc.add(new Field("introduction",ans.getString("introduction"),Field.Store.NO,Field.Index.ANALYZED));
            return doc;
        }
        public void AddIndex(int tid) throws IOException {
            try {
                writer.addDocument(GetDocument(tid));
            } catch (SQLException ex) {
                Logger.getLogger(FullText.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void DelIndex(int tid) throws IOException {
            writer.deleteDocuments(new Term("id",Integer.toString(tid)));
        }
        public void UpdateIndex(int tid) throws IOException {
            try {
                writer.updateDocument(new Term("id",Integer.toString(tid)), GetDocument(tid));
            } catch (SQLException ex) {
                Logger.getLogger(FullText.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void Close() throws IOException {
            writer.close();
        }
    }
    public class Searcher {
        
    }
}
