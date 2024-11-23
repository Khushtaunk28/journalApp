package net.khushtaunk.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.Entity.journalEntry;
import net.khushtaunk.journalApp.dto.JournalDTO;
import net.khushtaunk.journalApp.service.JournalEntryService;
import net.khushtaunk.journalApp.service.userEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name="Journal Api's",description = "Read ,Post ,update or delete Journals")

public class JournalEntryController_v2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private userEntryService userService;
    @GetMapping()
    @Operation(summary = "Used to get all journal Entries")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user = userService.findByUsername(username);
//        return journalEntryService.getALl();
        List<journalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @Operation(summary = "Create your journal entry for respective user" ,description= "Tell the sentiment out of the listed Below /n HAPPY,\n" +
            "    SAD,\n" +
            "    ANGRY,\n" +
            "    ANXIOUS")
    public ResponseEntity<journalEntry> createEntry(@RequestBody JournalDTO entry) {
        //journalEntryService.saveEntry(entry);
        journalEntry newEntry = new journalEntry();
        newEntry.setTitle(entry.getTitle());
        newEntry.setContent(entry.getContent());
        newEntry.setSentiment(entry.getSentiment());
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            journalEntryService.saveEntry(newEntry, userName);

            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Get user by ID")
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId) {
        ObjectId objectId=new ObjectId(myId);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user = userService.findByUsername(username);
       // List<journalEntry> collect = (List<journalEntry>) user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId));
        List<journalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(objectId))
                .collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<journalEntry> journalEntry=journalEntryService.getEntryById(objectId);
            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

        }


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete User by user id")
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String myId) {
        ObjectId objectId=new ObjectId(myId);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean removed= journalEntryService.deleteEntryById(objectId,username);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "update or make changes in journal entry by user id")
    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable  String id, @RequestBody JournalDTO entry) {
        journalEntry newEntry = new journalEntry();
        newEntry.setTitle(entry.getTitle());
        newEntry.setContent(entry.getContent());
        newEntry.setSentiment(entry.getSentiment());
        ObjectId objectId=new ObjectId(id);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user = userService.findByUsername(username);
        List<journalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(objectId))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<journalEntry> oldjournalEntry = journalEntryService.getEntryById(objectId);
            if (oldjournalEntry.isPresent()) {
                journalEntry oldEntry = oldjournalEntry.get();
                oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
