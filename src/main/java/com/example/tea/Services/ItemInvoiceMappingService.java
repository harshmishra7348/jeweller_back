package com.example.tea.Services;

import com.example.tea.Model.InvoiceMST;
import com.example.tea.Model.ItemInvoiceMapping;
import com.example.tea.Model.ItemMST;
import com.example.tea.Repository.ItemInvoiceMappingRepository;
import com.example.tea.Repository.ItemMSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemInvoiceMappingService {
    @Autowired
    private ItemInvoiceMappingRepository itemInvoiceMappingRepository;
    @Autowired
    private ItemMSTService itemMSTService;
    @Autowired
    private ItemMSTRepository itemMSTRepository;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public List<ItemInvoiceMapping>  createAll(List<ItemMST> itemMSTList, InvoiceMST invoiceMST){
        if(itemMSTList.isEmpty()){
            throw new RuntimeException("please Select items.");
        }
        List<ItemMST> itemMSTS = new ArrayList<>();
        List<ItemInvoiceMapping> itemInvoiceMappings = new ArrayList<>();
        for(ItemMST itemMST:itemMSTList){
            ItemMST availableItemMST = itemMSTService.getById(itemMST.getId());
            ItemInvoiceMapping itemInvoiceMapping = new ItemInvoiceMapping();
            itemInvoiceMapping.setItemMST(availableItemMST);
            itemInvoiceMapping.setItemMSTId(itemMST.getId());
            //remaining to calculate quantity of items
            if(availableItemMST.getQuantity()<=0 || availableItemMST.getQuantity()<itemMST.getQuantity()){
                throw new RuntimeException("Item quantity is not available");
            }else{
                availableItemMST.setQuantity(availableItemMST.getQuantity()-itemMST.getQuantity());
                itemMSTS.add(availableItemMST);
            }
            itemInvoiceMapping.setQuantity(itemMST.getQuantity());
            itemInvoiceMapping.setAmount(itemMST.getPrice());
            if(availableItemMST.getSubUnit()!=null && !availableItemMST.getSubUnit().isEmpty()){
                itemInvoiceMapping.setTotalAmount(Double.parseDouble(decimalFormat.format(itemMST.getQuantity()*(availableItemMST.getPerUnitQuantity()*availableItemMST.getSellPrice()))));
            }else {
                itemInvoiceMapping.setTotalAmount(Double.parseDouble(decimalFormat.format(itemMST.getQuantity()*availableItemMST.getSellPrice())));
            }

            itemInvoiceMapping.setInvoiceMST(invoiceMST);
            itemInvoiceMapping.setInvoiceMSTId(invoiceMST.getId());
            itemInvoiceMappings.add(itemInvoiceMapping);
        }
        itemMSTRepository.saveAll(itemMSTS);
        return itemInvoiceMappingRepository.saveAll(itemInvoiceMappings);
    }
    public List<ItemInvoiceMapping> updateAll(List<ItemInvoiceMapping> itemMSTList,InvoiceMST invoiceMST){
        List<ItemInvoiceMapping> invoiceMappingList = new ArrayList<>();
        for(ItemInvoiceMapping itemInvoiceMapping:itemMSTList) {
            ItemMST byId = itemMSTService.getById(itemInvoiceMapping.getItemMSTId());
            //remaining to calculate quantity of items
            itemInvoiceMapping.setInvoiceMST(invoiceMST);
            invoiceMappingList.add(itemInvoiceMapping);
        }
        return itemInvoiceMappingRepository.saveAll(invoiceMappingList);
    }
    public List<ItemInvoiceMapping> getByInvoiceMSTId(Long invoiceMSTId){
        return itemInvoiceMappingRepository.findByInvoiceMSTId(invoiceMSTId);
    }
    public ItemInvoiceMapping getByItemInvoiceMappingId(Long itemInvoiceMappingId){
        return itemInvoiceMappingRepository.findById(itemInvoiceMappingId).orElseThrow(()->new RuntimeException("ItemInvoiceMapping Id not found."));
    }

}
