package com.anup.v1.JournalApp.entity;

import com.anup.v1.JournalApp.constants.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "journal_entry")
@Data
@NoArgsConstructor
/*
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
*/
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}
